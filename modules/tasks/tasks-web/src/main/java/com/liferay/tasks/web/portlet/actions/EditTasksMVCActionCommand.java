package com.liferay.tasks.web.portlet.actions;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.tasks.constants.TasksEntryPortletKeys;
import com.liferay.tasks.model.TasksEntry;
import com.liferay.tasks.service.TasksEntryLocalService;
import com.liferay.tasks.service.TasksEntryService;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + TasksEntryPortletKeys.TASKS,
			"mvc.command.name=/tasks/edit_tasksentry"
		},
		service = MVCActionCommand.class
	)

public class EditTasksMVCActionCommand extends BaseMVCActionCommand {


	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);
		
		if (cmd.equals(Constants.ADD)) {
			updateTasksEntry(actionRequest, actionResponse);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			updateTasksEntryStatus(actionRequest, actionResponse);
		}
		else if (cmd.equals(Constants.DELETE)) {
			deleteTasksEntry(actionRequest, actionResponse);
		}	
	}
	
	public void deleteTasksEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tasksEntryId = ParamUtil.getLong(actionRequest, "tasksEntryId");

		_tasksEntryLocalService.deleteTasksEntry(tasksEntryId);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
		else {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("success", Boolean.TRUE);

			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			ServletResponseUtil.write(response, jsonObject.toString());
		}
	}

	public void updateTasksEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long tasksEntryId = ParamUtil.getLong(actionRequest, "tasksEntryId");

		String title = ParamUtil.getString(actionRequest, "title");
		int priority = ParamUtil.getInteger(actionRequest, "priority");
		long assigneeUserId = ParamUtil.getLong(
			actionRequest, "assigneeUserId");
		long resolverUserId = ParamUtil.getLong(
			actionRequest, "resolverUserId");

		int dueDateMonth = ParamUtil.getInteger(actionRequest, "dueDateMonth");
		int dueDateDay = ParamUtil.getInteger(actionRequest, "dueDateDay");
		int dueDateYear = ParamUtil.getInteger(actionRequest, "dueDateYear");
		int dueDateHour = ParamUtil.getInteger(actionRequest, "dueDateHour");
		int dueDateMinute = ParamUtil.getInteger(
			actionRequest, "dueDateMinute");
		int dueDateAmPm = ParamUtil.getInteger(actionRequest, "dueDateAmPm");

		if (dueDateAmPm == Calendar.PM) {
			dueDateHour += 12;
		}

		boolean addDueDate = ParamUtil.getBoolean(actionRequest, "addDueDate");
		int status = ParamUtil.getInteger(actionRequest, "status");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			TasksEntry.class.getName(), actionRequest);

		TasksEntry taskEntry = null;

		try {
			if (tasksEntryId <= 0) {
				taskEntry = _tasksEntryService.addTasksEntry(
					title, priority, assigneeUserId, dueDateMonth, dueDateDay,
					dueDateYear, dueDateHour, dueDateMinute, addDueDate,
					serviceContext);
			}
			else {
				taskEntry = _tasksEntryService.updateTasksEntry(
					tasksEntryId, title, priority, assigneeUserId,
					resolverUserId, dueDateMonth, dueDateDay, dueDateYear,
					dueDateHour, dueDateMinute, addDueDate, status,
					serviceContext);
			}

			Layout layout = themeDisplay.getLayout();

			PortletURL portletURL = PortletURLFactoryUtil.create(
				actionRequest, TasksEntryPortletKeys.TASKS, layout.getPlid(),
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter("mvcPath", "/tasks/view_task.jsp");
			portletURL.setParameter(
				"tasksEntryId", String.valueOf(taskEntry.getTasksEntryId()));
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			actionResponse.sendRedirect(portletURL.toString());
		}
		catch (AssetTagException ate) {
			actionResponse.setRenderParameter(
				"mvcPath", "/tasks/edit_task.jsp");

			actionResponse.setRenderParameters(actionRequest.getParameterMap());

			SessionErrors.add(actionRequest, ate.getClass(), ate);
		}
	}

	public void updateTasksEntryStatus(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long tasksEntryId = ParamUtil.getLong(actionRequest, "tasksEntryId");

		long resolverUserId = ParamUtil.getLong(
			actionRequest, "resolverUserId");
		int status = ParamUtil.getInteger(actionRequest, "status");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			TasksEntry.class.getName(), actionRequest);

		_tasksEntryLocalService.updateTasksEntryStatus(
			tasksEntryId, resolverUserId, status, serviceContext);

		Layout layout = themeDisplay.getLayout();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, TasksEntryPortletKeys.TASKS, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/tasks/view_task.jsp");
		portletURL.setParameter("tasksEntryId", String.valueOf(tasksEntryId));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		actionResponse.sendRedirect(portletURL.toString());
	}
	@Reference(unbind = "-")
	protected void setTasksEntryService(
			TasksEntryService tasksEntryService) {

		_tasksEntryService = tasksEntryService;
	}
	
	@Reference(unbind = "-")
	protected void setTasksEntryLocalService(
			TasksEntryLocalService tasksEntryLocalService) {

		_tasksEntryLocalService = tasksEntryLocalService;
	}
	
	private static TasksEntryService _tasksEntryService;
	private static TasksEntryLocalService _tasksEntryLocalService;
}
