package io.asiam.tansiq.services.results_service;

import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.repositories.MajorInfoRepository;
import io.asiam.tansiq.repositories.MajorRepository;
import io.asiam.tansiq.repositories.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultsService {
   private final EntityManager entityManager;
   private final MajorInfoRepository majorInfoRepository;
   private final ResultRepository resultRepository;
   private final MajorRepository majorRepository;

   public void preMatchingCleanup() {
      // clear any previous major info
      majorInfoRepository.deleteAll();
      // clear all results
      resultRepository.deleteAll();
      // reset all limits to original limit
      List<Major> majors = majorRepository.findAll();
      majors.forEach(major -> {
         major.setLimit(major.getOriginalLimit());
         major.setStudentsCount(0);
      });
      majorRepository.saveAll(majors);
   }
   public boolean isResultsFull() {
      Query q = entityManager.createNativeQuery(
              "SELECT 1 FROM \"RESULT\" WHERE ROWNUM=1"
      );
      return q.getResultList().size() != 0;
   }
}
