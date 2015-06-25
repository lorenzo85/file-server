package org.fileserver.mediatypes;

import org.springframework.http.MediaType;

public class ApplicationMediaTypes extends MediaType {

    public static final MediaType MICROSOFT_EXCEL;
    public static final String MICROSOFT_EXCEL_VALUE = "application/vnd.ms-excel";
    public static final MediaType MICROSOFT_WORD;
    public static final String MICROSOFT_WORD_VALUE = "application/msword";
    public static final MediaType MICROSOFT_WORD_DOCUMENT_MACRO_ENABLED;
    public static final String MICROSOFT_WORD_DOCUMENT_MACRO_ENABLED_VALUE = "application/vnd.ms-word.document.macroEnabled.12";
    public static final MediaType MICROSOFT_WORD_TEMPLATE_MACRO_ENABLED;
    public static final String MICROSOFT_WORD_TEMPLATE_MACRO_ENABLED_VALUE = "application/vnd.ms-word.document.macroEnabled.12";
    public static final MediaType OPENXML_OFFICE_WORD_DOCUMENT;
    public static final String OPENXML_OFFICE_WORD_DOCUMENT_VALUE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final MediaType OPENXML_OFFICE_WORD_TEMPLATE;
    public static final String OPENXML_OFFICE_WORD_TEMPLATE_VALUE = "application/vnd.openxmlformats-officedocument.wordprocessingml.template";
    public static final MediaType OPENXML_OFFICE_SPREADSHEET_SHEET;
    public static final String OPENXML_OFFICE_SPREADSHEET_SHEET_VALUE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final MediaType OPENXML_OFFICE_SPREADSHEET_TEMPLATE;
    public static final String OPENXML_OFFICE_SPREADSHEET_TEMPLATE_VALUE = "application/vnd.openxmlformats-officedocument.spreadsheetml.template";
    public static final MediaType MICROSOFT_EXCEL_SHEET_MACRO_ENABLED;
    public static final String MICROSOFT_EXCEL_SHEET_MACRO_ENABLED_VALUE = "application/vnd.ms-excel.sheet.macroEnabled.12";
    public static final MediaType MICROSOFT_EXCEL_TEMPLATE_MACRO_ENABLED;
    public static final String MICROSOFT_EXCEL_TEMPLATE_MACRO_ENABLED_VALUE = "application/vnd.ms-excel.template.macroEnabled.12";
    public static final MediaType MICROSOFT_EXCEL_ADDIN_MACRO_ENABLED;
    public static final String MICROSOFT_EXCEL_ADDIN_MACRO_ENABLED_VALUE = "application/vnd.ms-excel.addin.macroEnabled.12";
    public static final MediaType MICROSOFT_EXCEL_SHEET_BINARY_MACRO_ENABLED;
    public static final String MICROSOFT_EXCEL_SHEET_BINARY_MACRO_ENABLED_VALUE = "application/vnd.ms-excel.sheet.binary.macroEnabled.12";
    public static final MediaType MICROSOFT_POWERPOINT;
    public static final String MICROSOFT_POWERPOINT_VALUE = "application/vnd.ms-powerpoint";
    public static final MediaType OPENXML_OFFICE_PRESENTATION_PRESENTATION;
    public static final String OPENXML_OFFICE_PRESENTATION_PRESENTATION_VALUE = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final MediaType OPENXML_OFFICE_PRESENTATION_TEMPLATE;
    public static final String OPENXML_OFFICE_PRESENTATION_TEMPLATE_VALUE = "application/vnd.openxmlformats-officedocument.presentationml.template";
    public static final MediaType OPENXML_OFFICE_PRESENTATION_SLIDESHOW;
    public static final String OPENXML_OFFICE_PRESENTATION_SLIDESHOW_VALUE = "application/vnd.openxmlformats-officedocument.presentationml.slideshow";
    public static final MediaType MICROSOFT_POWERPOINT_ADDIN_MACRO_ENABLED;
    public static final String MICROSOFT_POWERPOINT_ADDIN_MACRO_ENABLED_VALUE = "application/vnd.ms-powerpoint.addin.macroEnabled.12";
    public static final MediaType MICROSOFT_POWERPOINT_PRESENTATION_MACRO_ENABLED;
    public static final String MICROSOFT_POWERPOINT_PRESENTATION_MACRO_ENABLED_VALUE = "application/vnd.ms-powerpoint.presentation.macroEnabled.12";
    public static final MediaType MICROSOFT_POWERPOINT_SLIDESHOW_MACRO_ENABLED;
    public static final String MICROSOFT_POWERPOINT_SLIDESHOW_MACRO_ENABLED_VALUE = "application/vnd.ms-powerpoint.slideshow.macroEnabled.12";

    public ApplicationMediaTypes(String type) {
        super(type);
    }

    static {
        MICROSOFT_EXCEL = valueOf(MICROSOFT_EXCEL_VALUE);
        MICROSOFT_WORD = valueOf(MICROSOFT_WORD_VALUE);
        OPENXML_OFFICE_WORD_DOCUMENT = valueOf(OPENXML_OFFICE_WORD_DOCUMENT_VALUE);
        OPENXML_OFFICE_WORD_TEMPLATE = valueOf(OPENXML_OFFICE_WORD_TEMPLATE_VALUE);
        MICROSOFT_WORD_DOCUMENT_MACRO_ENABLED = valueOf(MICROSOFT_WORD_DOCUMENT_MACRO_ENABLED_VALUE);
        MICROSOFT_WORD_TEMPLATE_MACRO_ENABLED = valueOf(MICROSOFT_WORD_TEMPLATE_MACRO_ENABLED_VALUE);
        OPENXML_OFFICE_SPREADSHEET_SHEET = valueOf(OPENXML_OFFICE_SPREADSHEET_SHEET_VALUE);
        OPENXML_OFFICE_SPREADSHEET_TEMPLATE = valueOf(OPENXML_OFFICE_SPREADSHEET_TEMPLATE_VALUE);
        MICROSOFT_EXCEL_SHEET_MACRO_ENABLED = valueOf(MICROSOFT_EXCEL_SHEET_MACRO_ENABLED_VALUE);
        MICROSOFT_EXCEL_TEMPLATE_MACRO_ENABLED = valueOf(MICROSOFT_EXCEL_TEMPLATE_MACRO_ENABLED_VALUE);
        MICROSOFT_EXCEL_ADDIN_MACRO_ENABLED = valueOf(MICROSOFT_EXCEL_ADDIN_MACRO_ENABLED_VALUE);
        MICROSOFT_EXCEL_SHEET_BINARY_MACRO_ENABLED = valueOf(MICROSOFT_EXCEL_SHEET_BINARY_MACRO_ENABLED_VALUE);
        MICROSOFT_POWERPOINT = valueOf(MICROSOFT_POWERPOINT_VALUE);
        OPENXML_OFFICE_PRESENTATION_PRESENTATION = valueOf(OPENXML_OFFICE_PRESENTATION_PRESENTATION_VALUE);
        OPENXML_OFFICE_PRESENTATION_TEMPLATE = valueOf(OPENXML_OFFICE_PRESENTATION_TEMPLATE_VALUE);
        OPENXML_OFFICE_PRESENTATION_SLIDESHOW = valueOf(OPENXML_OFFICE_PRESENTATION_SLIDESHOW_VALUE);
        MICROSOFT_POWERPOINT_ADDIN_MACRO_ENABLED = valueOf(MICROSOFT_POWERPOINT_ADDIN_MACRO_ENABLED_VALUE);
        MICROSOFT_POWERPOINT_PRESENTATION_MACRO_ENABLED = valueOf(MICROSOFT_POWERPOINT_PRESENTATION_MACRO_ENABLED_VALUE);
        MICROSOFT_POWERPOINT_SLIDESHOW_MACRO_ENABLED = valueOf(MICROSOFT_POWERPOINT_SLIDESHOW_MACRO_ENABLED_VALUE);
    }

}
