package com.apinotas.notas.utils;
import com.apinotas.notas.exceptions.PdfGenerationException;
import com.apinotas.notas.services.dtos.response.StudentWithGradesResponseDTO;
import com.apinotas.notas.services.dtos.response.SubjectsWithStudentsResponseDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfUtils {

    public static ByteArrayOutputStream generatePdfStream(List<SubjectsWithStudentsResponseDTO> subjects) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            for (SubjectsWithStudentsResponseDTO subject : subjects) {
                Font subjectFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
                Paragraph subjectTitle = new Paragraph(subject.getName(), subjectFont);
                document.add(subjectTitle);
                document.add(new Paragraph("\n"));

                int maxGrades = subject.getStudents().stream()
                        .mapToInt(student -> student.getGrades().size())
                        .max()
                        .orElse(0);

                PdfPTable table = new PdfPTable(2 + maxGrades);
                table.setWidthPercentage(100);

                Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
                table.addCell(new PdfPCell(new Paragraph("Name", boldFont)));
                for (int i = 1; i <= maxGrades; i++) {
                    table.addCell(new PdfPCell(new Paragraph("Grade " + i, boldFont)));
                }
                table.addCell(new PdfPCell(new Paragraph("Average", boldFont)));

                for (StudentWithGradesResponseDTO student : subject.getStudents()) {
                    table.addCell(new PdfPCell(new Paragraph(student.getName())));

                    List<Double> grades = student.getGrades();
                    for (Double grade : grades) {
                        table.addCell(new PdfPCell(new Paragraph(grade.toString())));
                    }

                    int emptyCells = maxGrades - grades.size();
                    for (int i = 0; i < emptyCells; i++) {
                        table.addCell(new PdfPCell(new Paragraph("")));
                    }
                    table.addCell(new PdfPCell(new Paragraph(student.getAverage().toString())));
                }
                document.add(table);
                document.add(new Paragraph("\n\n"));
            }
        } catch (DocumentException e) {
            throw new PdfGenerationException("Error generating PDF", e);
        } finally {
                document.close();
        }
        return outputStream;
    }

    private PdfUtils() {
    }
}