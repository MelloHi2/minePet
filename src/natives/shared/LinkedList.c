/*
 * LinkedList.c
 *
 *  Created on: Oct 14, 2017
 *      Author: lucky-fish
 */

#include "LinkedList.h"
#include <memory.h>

struct LinkedList * makeLinkedList()
{
	return (struct LinkedList *)malloc(sizeof(struct LinkedList));
}

bool destroyLinkedList(struct LinkedList * list, bool destroyData)
{
	if (list == NULL)
		return false;
	list->current = list->first;
	while (list->current->next)
		list->current = list->current->next;

	while (list->current->prev)
	{
		if (destroyData && list->current->data)
			free(list->current->data);
		list->current = list->current->prev;
		free(list->current->next);
	}

	free(list);
	return true;
}

struct Node * getNodeAt(struct LinkedList * list, int index)
{
	if (list == NULL)
		return NULL;
	if (index < 0)
		return NULL;
	if (list->first == NULL)
		return NULL;
	list->current = list->first;
	for (int i = 0;i < index; i ++)
	{
		if (!list->current->next)
			return NULL;
		list->current = list->current->next;
	}

	return list->current;
}
struct Node * getLast(struct LinkedList * list)
{
	if (list == NULL)
		return NULL;

	list->current = list->first;

	while (list->current->next)
		list->current = list->current->next;

	return list->current;
}

void * get(struct LinkedList * list, int index)
{
	if (list == NULL)
		return NULL;
	if (index < 0)
		return NULL;

	struct Node * node = getNodeAt(list, index);
	if (node == NULL)
		return NULL;

	return node->data;
}

bool add(struct LinkedList * list, void * data)
{
	if (list == NULL)
		return false;

	struct Node * new = (struct Node *)malloc(sizeof(struct Node));
	memset(new, 0, sizeof(struct Node));

	new->data = data;

	if (list->first == NULL)
	{
		list->first = new;
		return true;
	}

	struct Node * last = getLast(list);

	new->prev = last;

	last->next = new;
	return true;
}

bool insert(struct LinkedList * list, void * data, int index)
{
	if (list == NULL || list->first == NULL)
		return false;
	if (index < 0)
		return false;

	struct Node * prev = getNodeAt(list, index);
	if (prev == NULL)
		return false;

	struct Node * next = prev->next;

	struct Node * current = (struct Node *)malloc(sizeof(struct Node));
	memset(current, 0, sizeof(struct Node));

	current->prev = prev;
	prev->next = current;

	current->next = next;
	if (next != NULL)
	{
		next->prev = current;
	}
	return true;
}

bool replace(struct LinkedList * list, void * data, int index, bool destroyOldData)
{
	if (list == NULL || list->first == NULL)
		return false;
	if (index < 0)
		return false;

	struct Node * replacingNode = getNodeAt(list, index);
	if (replacingNode == NULL)
		return false;

	if (destroyOldData)
		free(replacingNode->data);
	replacingNode->data = data;

	return true;
}

bool remove(struct LinkedList * list, int index, bool destroyOldData)
{
	if (list == NULL || list->first == NULL)
		return false;
	if (index < 0)
		return false;

	struct Node * removingNode = getNodeAt(list, index);

	if (removingNode == NULL)
		return false;

	if (destroyOldData && removingNode->data != NULL)
		free(removingNode->data);

	removingNode->prev->next = removingNode->next;
	removingNode->next->prev = removingNode->prev;

	free(removingNode);

	return true;
}

int getIndex(struct LinkedList * list, void * data)
{
	if (list == NULL || list->first == NULL)
		return -1;

	list->current = list->first;

	int i = 0;

	bool found;
	while (list->current->next)
	{
		i ++;
		if (list->current->data == data)
		{
			found = true;
			break;
		}
	}

	return found ? i : -1;
}

