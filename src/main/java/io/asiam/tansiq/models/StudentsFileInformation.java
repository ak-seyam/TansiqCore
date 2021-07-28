package io.asiam.tansiq.models;

import lombok.Data;

@Data
public class StudentsFileInformation {
   private final String studentEmailColumnName;
   private final String fileUrl;
   private final String studentsNameColumnName;
   private final String studentsMarkColumnName;
   private final String studentPasswordColumnName;

   public StudentsFileInformation(String fileName, String studentNameColumnName, String studentMarkColumnName, String studentEmailColumnName, String studentPasswordColumnName) {
      this.fileUrl = fileName;
      this.studentsNameColumnName = studentNameColumnName;
      this.studentsMarkColumnName = studentMarkColumnName;
      this.studentEmailColumnName = studentEmailColumnName;
      this.studentPasswordColumnName = studentPasswordColumnName;
   }
}
