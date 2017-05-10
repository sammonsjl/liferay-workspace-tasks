/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.tasks.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import com.liferay.tasks.model.TasksEntry;

/**
 * Provides the remote service interface for TasksEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Ryan Park
 * @see TasksEntryServiceUtil
 * @see com.liferay.tasks.service.base.TasksEntryServiceBaseImpl
 * @see com.liferay.tasks.service.impl.TasksEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=tms", "json.web.service.context.path=TasksEntry"}, service = TasksEntryService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface TasksEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TasksEntryServiceUtil} to access the tasks entry remote service. Add custom service methods to {@link com.liferay.tasks.service.impl.TasksEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public TasksEntry addTasksEntry(java.lang.String title, int priority,
		long assigneeUserId, int dueDateMonth, int dueDateDay, int dueDateYear,
		int dueDateHour, int dueDateMinute, boolean neverDue,
		ServiceContext serviceContext) throws PortalException, SystemException;

	public TasksEntry deleteTasksEntry(long tasksEntryId)
		throws PortalException, SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TasksEntry getTasksEntry(long tasksEntryId)
		throws PortalException, SystemException;

	public TasksEntry updateTasksEntry(long tasksEntryId,
		java.lang.String title, int priority, long assigneeUserId,
		long resolverUserId, int dueDateMonth, int dueDateDay, int dueDateYear,
		int dueDateHour, int dueDateMinute, boolean neverDue, int status,
		ServiceContext serviceContext) throws PortalException, SystemException;

	public TasksEntry updateTasksEntryStatus(long tasksEntryId,
		long resolverUserId, int status, ServiceContext serviceContext)
		throws PortalException, SystemException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();
}