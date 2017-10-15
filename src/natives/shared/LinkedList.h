/*
 * LinkedList.h
 *
 *  Created on: Oct 14, 2017
 *      Author: lucky-fish
 */

#ifndef LINKEDLIST_H_
#define LINKEDLIST_H_

#include <stdbool.h>

struct Node
{
	struct Node * prev;
	void * data;
	struct Node * next;
};

struct LinkedList
{
	struct Node * first;
	struct Node * current;
};

struct LinkedList * makeLinkedList();
bool destroyLinkedList(struct LinkedList * list, bool destroyData);
void * get(struct LinkedList * list, int index);
bool add(struct LinkedList * list, void * data);
bool insert(struct LinkedList * list, void * data, int index);
bool replace(struct LinkedList * list, void * data, int index, bool destroyOldData);
bool remove(struct LinkedList * list, int index, bool destroyOldData);
int getIndex(struct LinkedList * list, void * data);

#endif /* LINKEDLIST_H_ */
