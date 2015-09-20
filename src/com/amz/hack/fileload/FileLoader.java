package com.amz.hack.fileload;

import java.io.File;
import java.util.List;

public interface FileLoader {
public List getLocalCopy(String fPath);
public List loadFileDetails(String filepath);
}
