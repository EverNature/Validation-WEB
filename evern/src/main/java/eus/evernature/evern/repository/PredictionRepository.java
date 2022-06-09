package eus.evernature.evern.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eus.evernature.evern.models.Prediction;

public interface PredictionRepository extends JpaRepository<Prediction, Integer> {

    @Query("SELECT p FROM prediction p WHERE p.correctorExpert IS NULL") 
    public List<Prediction> findAll();
    
    @Query("SELECT p FROM prediction p WHERE p.correctorExpert IS NULL ORDER BY p.id DESC") 
    public Page<Prediction> findAllSorted(Pageable pageable);
}
