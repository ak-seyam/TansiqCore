package io.asiam.tansiq.models;

import lombok.Data;

@Data
public class StudentsFileInformation {
   private String fileName;
   private String studentNameColumnName;
   private String studentMarkColumnName;

   public StudentsFileInformation(String fileName, String studentNameColumnName, String studentMarkColumnName) {
      this.fileName = fileName;
      this.studentNameColumnName = studentNameColumnName;
      this.studentMarkColumnName = studentMarkColumnName;
   }
}
