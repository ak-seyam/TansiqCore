package io.asiam.tansiq.models;

import lombok.Data;

@Data
public class StudentsFileInformation {
   private String fileUrl;
   private String studentsNameColumnName;
   private String studentsMarkColumnName;

   public StudentsFileInformation(String fileName, String studentNameColumnName, String studentMarkColumnName) {
      this.fileUrl = fileName;
      this.studentsNameColumnName = studentNameColumnName;
      this.studentsMarkColumnName = studentMarkColumnName;
   }
}
