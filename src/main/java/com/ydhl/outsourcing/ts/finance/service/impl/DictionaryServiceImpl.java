package com.ydhl.outsourcing.ts.finance.service.impl;

import com.ydhl.outsourcing.ts.finance.dao.DictionaryDao;
import com.ydhl.outsourcing.ts.finance.dto.DictionaryDto;
import com.ydhl.outsourcing.ts.finance.model.Dictionary;
import com.ydhl.outsourcing.ts.finance.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martins
 * @create 2018/1/17 18:40.
 * @description
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryDao dictionaryDao;

    @Override
    public List<DictionaryDto> getAllDictionary() {
        List<Dictionary> dictionaryList = dictionaryDao.getAllDictionary();
        List<DictionaryDto> dictionaryDtoList = new ArrayList<>();
        for (Dictionary dictionary : dictionaryList) {
            DictionaryDto dictionaryDto = new DictionaryDto();
            dictionaryDto.setType(dictionary.getType());
            dictionaryDto.setCode(dictionary.getCode());
            dictionaryDto.setValue(dictionary.getValue());
            dictionaryDto.setSeq(dictionary.getSeq());
            dictionaryDtoList.add(dictionaryDto);
        }
        return dictionaryDtoList;
    }
}
