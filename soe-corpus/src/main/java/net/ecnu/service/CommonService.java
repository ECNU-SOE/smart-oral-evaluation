package net.ecnu.service;


import java.io.IOException;

public interface CommonService {
    Object listMotherTongue();

    Object listMotherTongue2() throws IOException;

    Object getCpsgrpIdByFirstLanguageId(int languageId);
}
