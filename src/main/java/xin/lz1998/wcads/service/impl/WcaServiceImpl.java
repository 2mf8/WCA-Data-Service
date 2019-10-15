package xin.lz1998.wcads.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.lz1998.wcads.Config;
import xin.lz1998.wcads.entity.*;
import xin.lz1998.wcads.repository.*;
import xin.lz1998.wcads.service.*;
import xin.lz1998.wcads.utils.DataImportUtil;
import xin.lz1998.wcads.utils.DownloadUtil;
import xin.lz1998.wcads.utils.UnZipUtil;

import java.io.File;

@Service
public class WcaServiceImpl implements WcaService {
    @Autowired
    WcaPersonRepository wcaPersonRepository;

    @Autowired
    WcaCompetitionRepository wcaCompetitionRepository;

    @Autowired
    WcaRankAverageRepository wcaRankAverageRepository;

    @Autowired
    WcaRankSingleRepository wcaRankSingleRepository;

    @Autowired
    WcaResultRepository wcaResultRepository;

    private static final String PERSONS_FILE = "WCA_export_Persons.tsv";
    private static final String COMPETITIONS_FILE = "WCA_export_Persons.tsv";
    private static final String RANKS_AVERAGE_FILE = "WCA_export_Persons.tsv";
    private static final String RANKS_SINGLE_FILE = "WCA_export_Persons.tsv";
    private static final String RESULTS_FILE = "WCA_export_Persons.tsv";

    private Logger logger =LoggerFactory.getLogger(WcaServiceImpl.class);

    @Override
    public void updateData(){
        DownloadUtil.download(Config.getWcaExportUrl(), Config.getWcaExportZip(), new DownloadUtil.OnDownloadListener() {
            private int percent=-1;
            @Override
            public void onDownloadSuccess(File file) {
                extractData();
                importData();
            }

            @Override
            public void onDownloading(int progress) {
                if(progress!=percent){
                    percent=progress;
                    logger.info("{}% downloaded",percent);
                }

            }

            @Override
            public void onDownloadFailed(Exception e) {
                System.out.println(e.toString());
            }
        });

    }

    @Override
    public void downloadData(){
        DownloadUtil.download(Config.getWcaExportUrl(), Config.getWcaExportZip(), new DownloadUtil.OnDownloadListener() {
            private int percent=-1;
            @Override
            public void onDownloadSuccess(File file) {
                System.out.println(file.toString());
            }

            @Override
            public void onDownloading(int progress) {
                if(progress!=percent){
                    percent=progress;
                    logger.info("{}% downloaded",percent);
                }

            }

            @Override
            public void onDownloadFailed(Exception e) {
                System.out.println(e.toString());
            }
        });
    }
    @Override
    public void extractData(){
        UnZipUtil.unzip(Config.getWcaExportZip(),Config.getWcaExtractPath());
    }

    @Transactional
    @Override
    public void importData() {
        importPersons();
        importCompetitions();
        importRanksAverage();
        importRanksSingle();
//        importResults();
        // TODO 这里可以导入其他数据，但是不常用，为了避免占内存就没写
    }

    @Transactional
    @Override
    public void importPersons() {
        String filepath = Config.getWcaExtractPath() + PERSONS_FILE;
        DataImportUtil.importData(filepath, wcaPersonRepository, WcaPerson.class);
    }

    @Transactional
    @Override
    public void importRanksAverage() {
        String filepath= Config.getWcaExtractPath()+RANKS_AVERAGE_FILE;
        DataImportUtil.importData(filepath, wcaRankAverageRepository, WcaRankAverage.class);
    }

    @Transactional
    @Override
    public void importRanksSingle() {
        String filepath= Config.getWcaExtractPath()+RANKS_SINGLE_FILE;
        DataImportUtil.importData(filepath, wcaRankSingleRepository, WcaRankSingle.class);
    }

    @Transactional
    @Override
    public void importResults() {
        String filepath = Config.getWcaExtractPath() + RESULTS_FILE;
        DataImportUtil.importData(filepath, wcaResultRepository, WcaResult.class);
    }

    @Transactional
    @Override
    public void importCompetitions() {
        String filepath = Config.getWcaExtractPath() + COMPETITIONS_FILE;
        DataImportUtil.importData(filepath, wcaCompetitionRepository, WcaCompetition.class);
    }
}
