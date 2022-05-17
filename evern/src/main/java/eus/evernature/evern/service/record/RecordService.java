package eus.evernature.evern.service.record;

import java.util.List;

import eus.evernature.evern.models.Prediction;
import eus.evernature.evern.models.Record;

public interface RecordService {
    Record saveRecord(Record record);
    void addPredictionToRecord(Integer recordId, Prediction prediction);
    Record getRecord(Integer recordId);
    List<Record> getRecords();
}
