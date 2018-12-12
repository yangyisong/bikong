package com.ydhl.outsourcing.ts.finance.service;

import com.ydhl.outsourcing.ts.finance.dto.DictionaryDto;

import java.util.List;

/**
 * @author Martins
 * @create 2018/1/17 18:39.
 */
public interface DictionaryService {

    /**
     * 获得所有字典数据
     *
     * @return
     */
    List<DictionaryDto> getAllDictionary();
}
