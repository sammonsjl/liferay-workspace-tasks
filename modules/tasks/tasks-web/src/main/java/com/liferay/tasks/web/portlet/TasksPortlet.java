/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This file is part of Liferay Social Office. Liferay Social Office is free
 * software: you can redistribute it and/or modify it under the terms of the GNU
 * Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Liferay Social Office is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Liferay Social Office. If not, see http://www.gnu.org/licenses/agpl-3.0.html.
 */

package com.liferay.tasks.web.portlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

/**
 * @author Ryan Park
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.add-default-resource=true",
			"com.liferay.portlet.css-class-wrapper=tasks-portlet",
			"com.liferay.portlet.header-portlet-css=/tasks/css/main.css",
			"com.liferay.portlet.header-portlet-javascript=/tasks/js/main.js",
			"com.liferay.portlet.display-category=category.collaboration",
			"com.liferay.portlet.instanceable=false",
			"javax.portlet.security-role-ref=power-user,user",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/tasks/view.jsp",
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.init-param.copy-request-parameters=false"
		},
		service = Portlet.class
)
public class TasksPortlet extends MVCPortlet {

}