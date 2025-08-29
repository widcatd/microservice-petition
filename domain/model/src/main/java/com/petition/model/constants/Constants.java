package com.petition.model.constants;

public class Constants {
    private Constants() {}
    public static final String LOG_PETITION_VALIDATOR_PROCESSING = "Validación completada para documento: {}, traceId: {}";
    public static final String LOG_USER_FOUND = "Usuario encontrado con email: {}, traceId: {}";
    public static final String LOG_PETITION_SAVED = "Solicitud guardada con ID: {}, traceId: {}";
    public static final String LOG_RESPONSE_SENT = "Respuesta enviada con status: {}, traceId: {}";
    public static final String LOG_ERROR_PROCESSING = "Error procesando la solicitud: {}, traceId: {}";
    public static final String LOG_REGISTER_NOT_FOUND = "Registro no encontrado: {}, traceId: {}";
    public static final String LOG_IDENTITY_NOT_FOUND = "Documento de identidad no encontrado: {}, traceId: {}";
    public static final String LOG_VALIDATION_ERROR = "Error de validación: {}, traceId: {}";

    public static final String LOG_REQUEST_RECEIVED = "Solicitud recibida, traceId: {}";
    public static final String LOG_REPO_START_SAVE = "Iniciando guardado de solicitud con LoanTypeId={}, traceId={}";
    public static final String LOG_REPO_LOAN_TYPE_FOUND = "LoanType encontrado con id={}, traceId={}";
    public static final String LOG_REPO_STATE_PROVIDED = "Solicitud ya contiene estado con id={}, traceId={}";
    public static final String LOG_REPO_SAVING_WITH_STATE = "Guardando solicitud con estado id={}, traceId={}";
    public static final String LOG_REPO_SAVED_SUCCESS = "Solicitud guardada con éxito id={}, traceId={}";
    public static final String LOG_REPO_ERROR_SAVING = "Error guardando Petition en repositorio, mensaje: {}, traceId: {}";


}
