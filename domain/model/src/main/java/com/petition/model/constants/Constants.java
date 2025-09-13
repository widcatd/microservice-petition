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
    public static final String LOG_REPO_START_UPDATE = "Iniciando actualizacion de solicitud con stateId={}, traceId={}";
    public static final String LOG_REPO_LOAN_TYPE_FOUND = "LoanType encontrado con id={}, traceId={}";
    public static final String LOG_REPO_STATE_PROVIDED = "Solicitud ya contiene estado con id={}, traceId={}";
    public static final String LOG_REPO_SAVING_WITH_STATE = "Guardando solicitud con estado id={}, traceId={}";
    public static final String LOG_REPO_SAVED_SUCCESS = "Solicitud guardada con éxito id={}, traceId={}";
    public static final String LOG_REPO_UPDATE_SUCCESS = "Solicitud actualizada con éxito id={}, traceId={}";
    public static final String LOG_REPO_ERROR_SAVING = "Error guardando solicitud en repositorio, mensaje: {}, traceId: {}";
    public static final String LOG_REPO_ERROR_UPDATE = "Error actualizando la solicitud en repositorio, mensaje: {}, traceId: {}";


    public static final String LOG_SEARCH_REQUEST_RECEIVED = "Búsqueda recibida con stateId={}, page={}, size={}, traceId={}";
    public static final String LOG_SEARCH_RESULTS_FOUND = "Resultados obtenidos={}, traceId={}";
    public static final String LOG_SEARCH_RESPONSE_SENT = "Respuesta enviada para búsqueda con status={}, traceId={}";
    public static final String LOG_SEARCH_ERROR = "Error procesando búsqueda: {}, traceId={}";

    public static final String LOG_REPO_START_SEARCH = "Iniciando búsqueda de Petitions con stateId={}, page={}, size={}, traceId={}";
    public static final String LOG_REPO_SEARCH_RESULT = "Petition encontrada con id={}, traceId={}";
    public static final String LOG_REPO_SEARCH_COMPLETED = "Búsqueda de Petitions completada, traceId={}";
    public static final String LOG_REPO_SEARCH_ERROR = "Error en búsqueda de Petitions: {}, traceId={}";



}
