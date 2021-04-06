#include "jvmti.h"
#include "jni.h"
#include <string.h> /* memset */
#include <unistd.h> /* close */
#include <stdlib.h>
#include <stdbool.h>

char* clazz;
char* method;
char* param;
char* ret;

jmethodID method_id = NULL;
bool instance = false;

jint entry_count = 0;
jvmtiLocalVariableEntry* table = NULL;

jmethodID object_to_string_method_id = NULL;
jclass array_clazz = NULL;
jmethodID arrays_to_string_b_method_id = NULL;
jmethodID arrays_to_string_s_method_id = NULL;
jmethodID arrays_to_string_i_method_id = NULL;
jmethodID arrays_to_string_j_method_id = NULL;
jmethodID arrays_to_string_f_method_id = NULL;
jmethodID arrays_to_string_d_method_id = NULL;
jmethodID arrays_to_string_z_method_id = NULL;
jmethodID arrays_to_string_c_method_id = NULL;
jmethodID arrays_to_string_l_method_id = NULL;

void JNICALL OnVMInit(jvmtiEnv *jvmti, JNIEnv* jni, jthread thread)
{
	jclass jclazz = (*jni)->FindClass(jni, clazz);
	char* signature = (char*) malloc(3 + strlen(param)+ strlen(ret));
	strcpy(signature, "(");
	strcat(signature, param);
	strcat(signature, ")");
	strcat(signature, ret);
	method_id = (*jni)->GetStaticMethodID(jni, jclazz, method, signature);
	if(method_id == NULL) {	
		(*jni)->ExceptionClear(jni);
		method_id = (*jni)->GetMethodID(jni, jclazz, method, signature);
		if(method_id == NULL) {
			fprintf(stderr, "FATAL ERROR: no such method!\n");
			exit(1);
		}
		instance = true;
	}
	free(signature);

	jvmtiError error = (*jvmti)->GetLocalVariableTable(jvmti, method_id, &entry_count, &table);
	if(error == JVMTI_ERROR_ABSENT_INFORMATION) {
		fprintf(stderr, "FATAL ERROR: no local variable table available for specified method!\n");
		exit(1);
	}

	object_to_string_method_id = (*jni)->GetMethodID(jni, (*jni)->FindClass(jni, "java/lang/Object"), "toString", "()Ljava/lang/String;");
	array_clazz = (*jni)->FindClass(jni, "java/util/Arrays"); array_clazz = (jclass) (*jni)->NewGlobalRef(jni, array_clazz);
	arrays_to_string_b_method_id = (*jni)->GetStaticMethodID(jni, array_clazz, "toString", "([B)Ljava/lang/String;");
	arrays_to_string_s_method_id = (*jni)->GetStaticMethodID(jni, array_clazz, "toString", "([S)Ljava/lang/String;");
	arrays_to_string_i_method_id = (*jni)->GetStaticMethodID(jni, array_clazz, "toString", "([I)Ljava/lang/String;");
	arrays_to_string_j_method_id = (*jni)->GetStaticMethodID(jni, array_clazz, "toString", "([J)Ljava/lang/String;");
	arrays_to_string_f_method_id = (*jni)->GetStaticMethodID(jni, array_clazz, "toString", "([F)Ljava/lang/String;");
	arrays_to_string_d_method_id = (*jni)->GetStaticMethodID(jni, array_clazz, "toString", "([D)Ljava/lang/String;");
	arrays_to_string_z_method_id = (*jni)->GetStaticMethodID(jni, array_clazz, "toString", "([Z)Ljava/lang/String;");
	arrays_to_string_c_method_id = (*jni)->GetStaticMethodID(jni, array_clazz, "toString", "([C)Ljava/lang/String;");
	arrays_to_string_l_method_id = (*jni)->GetStaticMethodID(jni, array_clazz, "toString", "([Ljava/lang/Object;)Ljava/lang/String;");
}

char* ToString(JNIEnv* jni, jvalue value, char* type) {
	const size_t size = 128;
	char* string = (char*) malloc(sizeof(char) * size);
	switch(type[0]) {
		case 'B': snprintf(string, size, "%dB", value.b); break;
		case 'S': snprintf(string, size, "%dS", value.s); break;
		case 'I': snprintf(string, size, "%d", value.i); break;
		case 'J': snprintf(string, size, "%ldL", value.j); break;
		case 'F': snprintf(string, size, "%fF", value.f); break;
		case 'D': snprintf(string, size, "%lfD", value.d); break;
		case 'Z': snprintf(string, size, "%s", value.z ? "true" : "false"); break;
		case 'C': snprintf(string, size, "\'%c\'", value.c); break;
		case 'L': {
			if(value.l != NULL) {
				if(strstr(type, "Ljava/lang/String;") == type) {
					jstring value_string = (jstring) value.l;
					const char* value_string_chars = (*jni)->GetStringUTFChars(jni, value_string, NULL);
					snprintf(string, size, "\"%s\"", value_string_chars);
					(*jni)->ReleaseStringUTFChars(jni, value_string, value_string_chars);
				} else {
					jstring value_string = (jstring) (*jni)->CallObjectMethod(jni, value.l, object_to_string_method_id);
					const char* value_string_chars = (*jni)->GetStringUTFChars(jni, value_string, NULL);
					char* end = strchr(type, ';');
					*end = '\0';
					snprintf(string, size, "{%s: %s}", type + 1, value_string_chars);
					*end = ';';
					(*jni)->ReleaseStringUTFChars(jni, value_string, value_string_chars);
				}
			} else {
				snprintf(string, size, "{type: null}");
			}
			break;
		}
		case '[': {
			if(value.l != NULL) {
				jmethodID to_string_method;
				switch(type[1]) {
					case 'B': to_string_method = arrays_to_string_b_method_id; break;
					case 'S': to_string_method = arrays_to_string_s_method_id; break;
					case 'I': to_string_method = arrays_to_string_i_method_id; break;
					case 'J': to_string_method = arrays_to_string_j_method_id; break;
					case 'F': to_string_method = arrays_to_string_f_method_id; break;
					case 'D': to_string_method = arrays_to_string_d_method_id; break;
					case 'Z': to_string_method = arrays_to_string_z_method_id; break;
					case 'C': to_string_method = arrays_to_string_c_method_id; break;
					case 'L': to_string_method = arrays_to_string_l_method_id; break;
					case '[': to_string_method = arrays_to_string_l_method_id; break;
					case 'V': to_string_method = arrays_to_string_l_method_id; break;
					default: to_string_method = NULL; break;
				}
				jstring value_string = (jstring) (*jni)->CallStaticObjectMethod(jni, array_clazz, to_string_method, value.l);
				const char* value_string_chars = (*jni)->GetStringUTFChars(jni, value_string, NULL);
				snprintf(string, size, "%s", value_string_chars);
				(*jni)->ReleaseStringUTFChars(jni, value_string, value_string_chars);
			} else {
				snprintf(string, size, "null");
			}
			break;
		}
		case 'V': snprintf(string, size, "void"); break;
		default: snprintf(string, size, "unknown"); break;
	}
	return string;
}

void PrintArgument(jvmtiEnv *jvmti, JNIEnv* jni, jthread thread, jvmtiLocalVariableEntry* table, int i, int* offset) {
	int index = i+*offset;
	char* type = table[i].signature;
	jvalue value;
	switch(type[0]) {
		case 'Z': (*jvmti)->GetLocalInt(jvmti, thread, 0, table[i].slot, (jint*) &value.z); break;
		case 'C': (*jvmti)->GetLocalInt(jvmti, thread, 0, table[i].slot, (jint*) &value.c); break;
		case 'B': (*jvmti)->GetLocalInt(jvmti, thread, 0, table[i].slot, (jint*) &value.b); break;
		case 'S': (*jvmti)->GetLocalInt(jvmti, thread, 0, table[i].slot, (jint*) &value.s); break;
		case 'I': (*jvmti)->GetLocalInt(jvmti, thread, 0, table[i].slot, (jint*) &value.i); break;
		case 'J': (*jvmti)->GetLocalLong(jvmti, thread, 0, table[i].slot, (jlong*) &value.j); break;
		case 'F': (*jvmti)->GetLocalFloat(jvmti, thread, 0, table[i].slot, (jfloat*) &value.f); break;
		case 'D': (*jvmti)->GetLocalDouble(jvmti, thread, 0, table[i].slot, (jdouble*) &value.d); break;
		case 'L': case '[': {
			(*jvmti)->GetLocalObject(jvmti, thread, 0, table[i].slot, (jobject*) &value.l);
			char* start = type;
			char* end = start;
			while(*end == '[') end++;
			if(*end == 'L') while(*end != ';') end++;
			else end++;
			end--;
			int diff = end-start;
			*offset += diff;
			break;
		}
	}
	char* string = ToString(jni, value, type);
	fprintf(stderr, "%s", string);
	free(string);
}

void PrintMethod(jvmtiEnv *jvmti, JNIEnv* jni, jthread thread)
{
	if(instance) {
		int offset = 0;
		PrintArgument(jvmti, jni, thread, table, 0, &offset);
	} else {
		fprintf(stderr, "%s", clazz);
	}
	fprintf(stderr, ".%s(", method);

	// traverse arguments
	int offset = 0;
	for(int i = (instance ? 1 : 0); i < entry_count; i++)
	{
		if(i != (instance ? 1 : 0)) fprintf(stderr, ", ");
		PrintArgument(jvmti, jni, thread, table, i, &offset);
	}

	fprintf(stderr, ")");
	fflush(stderr);
}

void JNICALL OnMethodEntry(jvmtiEnv *jvmti, JNIEnv* jni, jthread thread, jmethodID method)
{
	if(method == method_id)
	{
		PrintMethod(jvmti, jni, thread);	
		fprintf(stderr, "\n");
		fflush(stderr);
	}
	
}

void JNICALL OnMethodExit(jvmtiEnv *jvmti, JNIEnv* jni, jthread thread, jmethodID method, jboolean was_popped_by_exception, jvalue return_value)
{
	if(method == method_id)
	{
		PrintMethod(jvmti, jni, thread);
		char* result_value_string = ToString(jni, return_value, ret);
		fprintf(stderr, " => %s\n", result_value_string);
		free(result_value_string);
		fflush(stderr);
	}
}

JNIEXPORT jint JNICALL Agent_OnLoad(JavaVM* _vm, char* options, void* reserved) 
{
	jvmtiEnv* jvmti = NULL;
	(*_vm)->GetEnv(_vm, (void**) &jvmti, JVMTI_VERSION);

	
	if (options != NULL && options[0] != '\0') {
		char* nextArgument = options;
		const char delimiters[] = ":=";
		char *key, *value;
		int i = 0;

		do {
			if(i==0) key = strtok(nextArgument, delimiters);
			else key = strtok(NULL, delimiters);

			if(key != NULL){
				value = strtok(NULL, delimiters);
				if(strstr(key, "clazz")!=NULL) clazz = value;
				else if(strstr(key, "method")!=NULL) method = value;
				else if(strstr(key, "param")!=NULL) param = value;
				else if(strstr(key, "ret")!=NULL) ret = value;
			}

			i++;
		} while(key != NULL); 
	}


 	jvmtiError error;
	jvmtiCapabilities requestedCapabilities, potentialCapabilities;
	memset(&requestedCapabilities, 0, sizeof(requestedCapabilities));

	// error checks
	if((error = (*jvmti)->GetPotentialCapabilities(jvmti, &potentialCapabilities)) != JVMTI_ERROR_NONE) 			
	return 0;

	if(potentialCapabilities.can_generate_method_entry_events) 
	{
	       requestedCapabilities.can_generate_method_entry_events = 1;
	}
	if(potentialCapabilities.can_generate_method_exit_events)
	{
		requestedCapabilities.can_generate_method_exit_events = 1;
	}
 	if(potentialCapabilities.can_access_local_variables)
	{
		requestedCapabilities.can_access_local_variables = 1;
	}

	// enable method entry and exit capabilities
	if((error = (*jvmti)->AddCapabilities(jvmti, &requestedCapabilities)) != JVMTI_ERROR_NONE) return 0;


	jvmtiEventCallbacks callbacks;
	memset(&callbacks, 0, sizeof(callbacks));
	callbacks.VMInit = OnVMInit;
	callbacks.MethodEntry = OnMethodEntry;
	callbacks.MethodExit = OnMethodExit;

	(*jvmti)->SetEventCallbacks(jvmti, &callbacks, sizeof(callbacks));
	(*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, JVMTI_EVENT_VM_INIT, NULL);
	(*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, JVMTI_EVENT_METHOD_ENTRY, NULL);
	(*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, JVMTI_EVENT_METHOD_EXIT, NULL);

	return 0;
}

JNIEXPORT void JNICALL Agent_UnLoad() {
	/*
	for(int i=0; i<entry_count; i++)
	{	
		(*jvmti)->Deallocate(jvmti, (unsigned char*) table[i].name);
		(*jvmti)->Deallocate(jvmti, (unsigned char*) table[i].signature);
		if (table[i].generic_signature != NULL) (*jvmti)->Deallocate(jvmti, (unsigned char*) table[i].generic_signature);
	}
	
	(*jvmti)->Deallocate(jvmti, (unsigned char*) table);
	*/
}


