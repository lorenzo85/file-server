package org.fileserver.mediatypes;

import org.springframework.http.MediaType;
import org.springframework.web.accept.MappingMediaTypeFileExtensionResolver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.fileserver.mediatypes.ApplicationMediaTypes.*;

public class MediaTypeFileExtensionResolver extends MappingMediaTypeFileExtensionResolver {

    private static final Map<String, MediaType> mediaTypeMap;
    static {
        Map<String, MediaType> aMap = new HashMap<>();
        aMap.put("xls", MICROSOFT_EXCEL);
        aMap.put("xlt", MICROSOFT_EXCEL);
        aMap.put("xla", MICROSOFT_EXCEL);
        aMap.put("doc", MICROSOFT_WORD);
        aMap.put("dot", MICROSOFT_WORD);
        aMap.put("docx", OPENXML_OFFICE_WORD_DOCUMENT);
        aMap.put("dotx", OPENXML_OFFICE_WORD_TEMPLATE);
        aMap.put("docm", MICROSOFT_WORD_DOCUMENT_MACRO_ENABLED);
        aMap.put("dotm", MICROSOFT_WORD_TEMPLATE_MACRO_ENABLED);
        aMap.put("xlsx", OPENXML_OFFICE_SPREADSHEET_SHEET);
        aMap.put("xltx", OPENXML_OFFICE_SPREADSHEET_TEMPLATE);
        aMap.put("xlsm", MICROSOFT_EXCEL_SHEET_MACRO_ENABLED);
        aMap.put("xltm", MICROSOFT_EXCEL_TEMPLATE_MACRO_ENABLED);
        aMap.put("xlam", MICROSOFT_EXCEL_ADDIN_MACRO_ENABLED);
        aMap.put("xlsb", MICROSOFT_EXCEL_SHEET_BINARY_MACRO_ENABLED);
        aMap.put("ppt", MICROSOFT_POWERPOINT);
        aMap.put("pot", MICROSOFT_POWERPOINT);
        aMap.put("pps", MICROSOFT_POWERPOINT);
        aMap.put("ppa", MICROSOFT_POWERPOINT);
        aMap.put("pptx", OPENXML_OFFICE_PRESENTATION_TEMPLATE);
        aMap.put("potx", OPENXML_OFFICE_PRESENTATION_TEMPLATE);
        aMap.put("ppsx", OPENXML_OFFICE_PRESENTATION_SLIDESHOW);
        aMap.put("ppam", MICROSOFT_POWERPOINT_ADDIN_MACRO_ENABLED);
        aMap.put("pptm", MICROSOFT_POWERPOINT_PRESENTATION_MACRO_ENABLED);
        aMap.put("potm", MICROSOFT_POWERPOINT_PRESENTATION_MACRO_ENABLED);
        aMap.put("ppsm", MICROSOFT_POWERPOINT_SLIDESHOW_MACRO_ENABLED);
        mediaTypeMap = Collections.unmodifiableMap(aMap);
    }

    public MediaTypeFileExtensionResolver() {
        super(mediaTypeMap);
    }

    public MediaType resolveMediaType(String givenMediaType, String fileName) {
        String[] segments = fileName.split("\\.");
        if (segments.length < 2) {
            return MediaType.parseMediaType(givenMediaType);
        } else {
            String extension = segments[segments.length - 1];
            return resolveMediaTypeByExtension(givenMediaType, extension);
        }
    }

    private MediaType resolveMediaTypeByExtension(String givenMediaType, String extension) {
        MediaType assignedMediaType = lookupMediaType(extension);
        if (assignedMediaType == null) {
            return MediaType.parseMediaType(givenMediaType);
        } else {
            return assignedMediaType;
        }
    }
}
