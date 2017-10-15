/*
 * PetWindow.c
 *
 *  Created on: Oct 14, 2017
 *      Author: lucky-fish
 */

#include "../shared/luckyfish_programs_minepet_pet_v1_0R0_display_PetWindow.h"
#include "../shared/LinkedList.h"
#include <gtk/gtk.h>
#include <jni.h>

struct LinkedList * idList = NULL;

typedef struct GTKWindow {
	   GtkWidget window = NULL;
	   GdkPixbuf pixbuf = NULL;
	   GdkBitmap bitmap = NULL;
	   GdkPixmap pixmap = NULL;
} GTKWINDOW;

JNIEXPORT jint JNICALL Java_luckyfish_programs_minepet_pet_v1_10R0_display_PetWindow_getWindowID(JNIEnv *, jclass)
{

}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved)
{
	idList = makeLinkedList();
	gtk_init(NULL, NULL);

	return 0;
}
