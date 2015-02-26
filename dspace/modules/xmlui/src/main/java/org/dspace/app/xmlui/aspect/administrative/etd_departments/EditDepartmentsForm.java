/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.xmlui.aspect.administrative.etd_departments;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.dspace.app.xmlui.aspect.administrative.FlowDepartmentUtils;
import org.dspace.app.xmlui.aspect.administrative.FlowGroupUtils;
import org.dspace.app.xmlui.cocoon.AbstractDSpaceTransformer;
import org.dspace.app.xmlui.wing.Message;
import org.dspace.app.xmlui.wing.WingException;
import org.dspace.app.xmlui.wing.element.Body;
import org.dspace.app.xmlui.wing.element.Cell;
import org.dspace.app.xmlui.wing.element.Division;
import org.dspace.app.xmlui.wing.element.Highlight;
import org.dspace.app.xmlui.wing.element.PageMeta;
import org.dspace.app.xmlui.wing.element.Para;
import org.dspace.app.xmlui.wing.element.Row;
import org.dspace.app.xmlui.wing.element.Table;
import org.dspace.app.xmlui.wing.element.Text;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.eperson.EPerson;
import org.dspace.content.EtdUnit;

/**
 * Present the user with the department's current state. The user may select to
 * change the department's name, OR search for new Collections to add, OR select
 * current collections for removal.
 *
 * @author Alexey Maslov
 * @author Scott Phillips
 */
public class EditDepartmentsForm extends AbstractDSpaceTransformer
{
	/** Language Strings */
	private static final Message T_dspace_home =
		message("xmlui.general.dspace_home");

	private static final Message T_department_trail =
		message("xmlui.administrative.departments.general.department_trail");

	private static final Message T_title =
		message("xmlui.administrative.departments.EditDepartmentForm.title");

	private static final Message T_trail =
		message("xmlui.administrative.departments.EditDepartmentForm.trail");

	private static final Message T_main_head =
		message("xmlui.administrative.departments.EditDepartmentForm.main_head");

	private static final Message T_collection_para =
		message("xmlui.administrative.departments.EditDepartmentForm.collection_para");

    private static final Message T_community_para =
        message("xmlui.administrative.departments.EditDepartmentForm.community_para");

	private static final Message T_label_name =
		message("xmlui.administrative.departments.EditDepartmentForm.label_name");

	private static final Message T_label_instructions =
		message("xmlui.administrative.departments.EditDepartmentForm.label_instructions");

	private static final Message T_label_search =
		message("xmlui.administrative.departments.EditDepartmentForm.label_search");

	private static final Message T_submit_search_collections =
			message("xmlui.administrative.departments.EditDepartmentForm.submit_search_collections");

//	private static final Message T_submit_search_people =
//		message("xmlui.administrative.departments.EditDepartmentForm.submit_search_people");
//
//	private static final Message T_submit_search_groups =
//		message("xmlui.administrative.departments.EditDepartmentForm.submit_search_groups");

	private static final Message T_no_results =
		message("xmlui.administrative.departments.EditDepartmentForm.no_results");

	private static final Message T_main_head_new =
		message("xmlui.administrative.departments.EditDepartmentForm.main_head_new");

	private static final Message T_submit_clear =
		message("xmlui.administrative.departments.EditDepartmentForm.submit_clear");

	private static final Message T_submit_save =
		message("xmlui.general.save");

	private static final Message T_submit_cancel =
		message("xmlui.general.cancel");

	private static final Message T_member =
		message("xmlui.administrative.departments.EditDepartmentForm.member");

//	private static final Message T_cycle =
//		message("xmlui.administrative.departments.EditDepartmentForm.cycle");

	private static final Message T_pending =
		message("xmlui.administrative.departments.EditDepartmentForm.pending");

	private static final Message T_pending_warn =
		message("xmlui.administrative.departments.EditDepartmentForm.pending_warn");

	private static final Message T_submit_add =
		message("xmlui.administrative.departments.EditDepartmentForm.submit_add");

	private static final Message T_submit_remove =
		message("xmlui.administrative.departments.EditDepartmentForm.submit_remove");

	// Collections Search
	private static final Message T_collections_column1 =
		message("xmlui.administrative.departments.EditDepartmentForm.collections_column1");

	private static final Message T_collections_column2 =
		message("xmlui.administrative.departments.EditDepartmentForm.collections_column2");

	private static final Message T_collections_column3 =
		message("xmlui.administrative.departments.EditDepartmentForm.collections_column3");

//	private static final Message T_collections_column4 =
//			message("xmlui.administrative.departments.EditDepartmentForm.collections_column4");

//	// EPeople Search
//	private static final Message T_epeople_column1 =
//		message("xmlui.administrative.departments.EditDepartmentForm.epeople_column1");
//
//	private static final Message T_epeople_column2 =
//		message("xmlui.administrative.departments.EditDepartmentForm.epeople_column2");
//
//	private static final Message T_epeople_column3 =
//		message("xmlui.administrative.departments.EditDepartmentForm.epeople_column3");
//
//	private static final Message T_epeople_column4 =
//		message("xmlui.administrative.departments.EditDepartmentForm.epeople_column4");
//
//	// Group Search
//	private static final Message T_groups_column1 =
//		message("xmlui.administrative.departments.EditDepartmentForm.groups_column1");
//
//	private static final Message T_groups_column2 =
//		message("xmlui.administrative.departments.EditDepartmentForm.groups_column2");
//
//	private static final Message T_groups_column3 =
//		message("xmlui.administrative.departments.EditDepartmentForm.groups_column3");
//
//	private static final Message T_groups_column4 =
//		message("xmlui.administrative.departments.EditDepartmentForm.groups_column4");
//
//	private static final Message T_groups_column5 =
//		message("xmlui.administrative.departments.EditDepartmentForm.groups_column5");
//
//	private static final Message T_groups_collection_link =
//		message("xmlui.administrative.departments.ManageDepartmentsMain.groups_collection_link");

	// Members
	private static final Message T_members_head =
		message("xmlui.administrative.departments.EditDepartmentForm.members_head");

	private static final Message T_members_column1 =
		message("xmlui.administrative.departments.EditDepartmentForm.members_column1");

	private static final Message T_members_column2 =
		message("xmlui.administrative.departments.EditDepartmentForm.members_column2");

	private static final Message T_members_column3 =
		message("xmlui.administrative.departments.EditDepartmentForm.members_column3");

//	private static final Message T_members_column4 =
//		message("xmlui.administrative.departments.EditDepartmentForm.members_column4");

	private static final Message T_members_department_name =
		message("xmlui.administrative.departments.EditDepartmentForm.members_department_name");

	private static final Message T_members_pending =
		message("xmlui.administrative.departments.EditDepartmentForm.members_pending");

	private static final Message T_members_none =
		message("xmlui.administrative.departments.EditDepartmentForm.members_none");


	// How many results to show on a page.
	private static final int RESULTS_PER_PAGE = 5;

	/** The maximum size of a collection name allowed */
	private static final int MAX_COLLECTION_NAME = 25;


	public void addPageMeta(PageMeta pageMeta) throws WingException
    {
        pageMeta.addMetadata("title").addContent(T_title);
        pageMeta.addTrailLink(contextPath + "/", T_dspace_home);
        pageMeta.addTrailLink(contextPath + "/admin/departments",T_department_trail);
        pageMeta.addTrail().addContent(T_trail);
    }


	public void addBody(Body body) throws WingException, SQLException
	{
		// Find the group in question
		int departmentID = parameters.getParameterAsInteger("departmentID",-1);
		String currentName = decodeFromURL(parameters.getParameter("departmentName",null));
		if (currentName == null || currentName.length() == 0)
        {
            currentName = FlowDepartmentUtils.getName(context, departmentID);
        }

		EtdUnit department = null;
		if (departmentID >= 0)
        {
            department = EtdUnit.find(context, departmentID);
        }

		// Find the collection or community if applicable
		Collection collection = null;
		Community community = null;
		if (department != null)
		{
			int collectionID = FlowDepartmentUtils.getCollectionId(department.getName());
			if (collectionID > -1)
            {
                collection = Collection.find(context, collectionID);
            }
			else
			{
			    int communityID = FlowDepartmentUtils.getCommunityId(department.getName());
			    if (communityID > -1)
                {
                    community = Community.find(context, communityID);
                }
		    }
		}

//		// Get list of member groups
//		String memberGroupIDsString = parameters.getParameter("memberGroupIDs",null);
//		List<Integer> memberGroupIDs = new ArrayList<Integer>();
//		if (memberGroupIDsString != null)
//		{
//			for (String id : memberGroupIDsString.split(","))
//			{
//				if (id.length() > 0)
//                {
//                    memberGroupIDs.add(Integer.valueOf(id));
//                }
//			}
//		}
//
//		// Get list of member epeople
//		String memberEPeopleIDsString = parameters.getParameter("memberEPeopleIDs",null);
//		List<Integer> memberEPeopleIDs = new ArrayList<Integer>();
//		if (memberEPeopleIDsString != null)
//		{
//			for (String id : memberEPeopleIDsString.split(","))
//            {
//				if (id.length() > 0)
//                {
//					memberEPeopleIDs.add(Integer.valueOf(id));
//                }
//            }
//		}

		// Get list of member collections
		String memberCollectionIDsString = parameters.getParameter("memberCollectionIDs",null);
		List<Integer> memberCollectionIDs = new ArrayList<Integer>();
		if (memberCollectionIDsString != null)
		{
			for (String id : memberCollectionIDsString.split(","))
			{
				if (id.length() > 0)
                {
                    memberCollectionIDs.add(Integer.valueOf(id));
                }
			}
		}

		// Get highlight parameters
//		int highlightEPersonID = parameters.getParameterAsInteger("highlightEPersonID",-1);
//		int highlightGroupID = parameters.getParameterAsInteger("highlightGroupID",-1);
		int highlightCollectionID = parameters.getParameterAsInteger("highlightCollectionID",-1);

		// Get search parameters
	    String query = decodeFromURL(parameters.getParameter("query",null));
	    int page     = parameters.getParameterAsInteger("page",0);
	    String type  = parameters.getParameter("type",null);

	    // Get any errors
	    String errorString = parameters.getParameter("errors",null);
		List<String> errors = new ArrayList<String>();
		if (errorString != null)
        {
			for (String error : errorString.split(","))
            {
				errors.add(error);
            }
        }

	    //DIVISION: department-edit
	    Division main = body.addInteractiveDivision("department-edit",contextPath+"/admin/departments",Division.METHOD_POST,"primary administrative departments");
	    if (department == null)
        {
            main.setHead(T_main_head_new);
        }
	    else
        {
            main.setHead(T_main_head.parameterize(department.getName(), departmentID));
        }


	    if(collection != null)
	    {
	    	Para para = main.addPara();
	    	para.addContent(T_collection_para);
	    	para.addXref(contextPath+"/handle/"+collection.getHandle(), collection.getMetadata("name"));
	    }
	    else if(community != null)
        {
            Para para = main.addPara();
            para.addContent(T_community_para);
            para.addXref(contextPath+"/handle/"+community.getHandle(), community.getMetadata("name"));
        }

	    // DIVISION: department-actions
	    Division actions = main.addDivision("department-edit-actions");
	    Para departmentName = actions.addPara();
        departmentName.addContent(T_label_name);
        Text departmentText = departmentName.addText("department_name");
        departmentText.setValue(currentName);
        if(collection != null || community != null)
        {
        	// If this group is associated with a collection or community then it is special,
        	// thus they shouldn't be able to update it.
        	departmentText.setDisabled();
        	departmentText.setHelp(T_label_instructions);
        }
        else if (errors.contains("department_name") || errors.contains("department_name_duplicate"))
        {
            departmentText.addError("");
        }


        Para searchBoxes = actions.addPara();
        searchBoxes.addContent(T_label_search);
        Text queryField = searchBoxes.addText("query");
        queryField.setValue(query);
        queryField.setSize(15);
//        searchBoxes.addButton("submit_search_epeople").setValue(T_submit_search_people);
//        searchBoxes.addButton("submit_search_groups").setValue(T_submit_search_groups);
        searchBoxes.addButton("submit_search_collections").setValue(T_submit_search_collections);


        if (query != null)
        {
        	if ("collection".equals(type))
	        {
	        	searchBoxes.addButton("submit_clear").setValue(T_submit_clear);
	        	addCollectionsSearch(main,query,page,department,memberCollectionIDs);
	        }
//        	else if ("eperson".equals(type))
//	        {
//	        	searchBoxes.addButton("submit_clear").setValue(T_submit_clear);
//	        	addEPeopleSearch(main,query,page,department,memberEPeopleIDs);
//	        }
//	        else if ("group".equals(type))
//	        {
//	        	searchBoxes.addButton("submit_clear").setValue(T_submit_clear);
//        		addGroupSearch(main,department,query,page,department,memberGroupIDs);
//	        }
        }

        boolean changes = false;
        if (department != null)
        {
            changes = addMemberList(main, department, memberCollectionIDs, highlightCollectionID);
        }


    	Para buttons = main.addPara();
    	buttons.addButton("submit_save").setValue(T_submit_save);
    	buttons.addButton("submit_cancel").setValue(T_submit_cancel);

    	if (changes)
        {
            main.addPara().addHighlight("warn").addContent(T_pending_warn);
        }


        main.addHidden("administrative-continue").setValue(knot.getId());
	}


	/**
	 * Search for collections to add to this department.
	 */
	private void addCollectionsSearch(Division div, String query, int page, EtdUnit department, List<Integer> memberCollectionIDs) throws SQLException, WingException
	{
        Collection[] collections = Collection.findAll(context);
        int resultCount = collections.length;

//      Collection[] collections = Collection.search(context, query, page*RESULTS_PER_PAGE, RESULTS_PER_PAGE);

		Division results = div.addDivision("results");

		if (resultCount > RESULTS_PER_PAGE)
		{
        	// If there are enough results then paginate the results
        	String baseURL = contextPath +"/admin/departments?administrative-continue="+knot.getId();
        	int firstIndex = page*RESULTS_PER_PAGE+1;
        	int lastIndex = page*RESULTS_PER_PAGE + collections.length;

        	String nextURL = null, prevURL = null;
        	if (page < (resultCount / RESULTS_PER_PAGE))
            {
                nextURL = baseURL + "&page=" + (page + 1);
            }
        	if (page > 0)
            {
                prevURL = baseURL + "&page=" + (page - 1);
            }

			results.setSimplePagination(resultCount,firstIndex,lastIndex,prevURL, nextURL);
		}

		/* Set up a table with search results (if there are any). */
		Table table = results.addTable("group-edit-search-collections",collections.length + 1, 1);
		Row header = table.addRow(Row.ROLE_HEADER);
		header.addCell().addContent(T_collections_column1);
		header.addCell().addContent(T_collections_column2);
		header.addCell().addContent(T_collections_column3);
//		header.addCell().addContent(T_collections_column4);

		for (Collection collection : collections)
		{
			String collectionID = String.valueOf(collection.getID());
			String name = collection.getName();
//			String email = collection.getEmail();
			String url = contextPath+"/admin/departments?administrative-continue="+knot.getId()+"&submit_edit_collection&collectionID="+collectionID;

			Row collectionData = table.addRow();

			collectionData.addCell().addContent(collection.getID());
			collectionData.addCell().addXref(url, name);
//			collectionData.addCell().addXref(url, email);

			// check if they are already a member of the group
			if (memberCollectionIDs.contains(collection.getID()))
			{
				// Check if they really members or just pending members
				if (department != null && department.isMember(collection))
                {
                    collectionData.addCellContent(T_member);
                }
				else
                {
                    collectionData.addCell().addHighlight("warn").addContent(T_pending);
                }
			}
			else
			{
				collectionData.addCell().addButton("submit_add_collection_"+collectionID).setValue(T_submit_add);
			}
		}

		if (collections.length <= 0) {
			table.addRow().addCell(1, 3).addContent(T_no_results);
		}
	}
//
//	/**
//	 * Search for epeople to add to this group.
//	 */
//	private void addEPeopleSearch(Division div, String query, int page, Group group, List<Integer> memberEPeopleIDs) throws SQLException, WingException
//	{
//		int resultCount = EPerson.searchResultCount(context, query);
//        EPerson[] epeople = EPerson.search(context, query, page*RESULTS_PER_PAGE, RESULTS_PER_PAGE);
//
//		Division results = div.addDivision("results");
//
//		if (resultCount > RESULTS_PER_PAGE)
//		{
//        	// If there are enough results then paginate the results
//        	String baseURL = contextPath +"/admin/groups?administrative-continue="+knot.getId();
//        	int firstIndex = page*RESULTS_PER_PAGE+1;
//        	int lastIndex = page*RESULTS_PER_PAGE + epeople.length;
//
//        	String nextURL = null, prevURL = null;
//        	if (page < (resultCount / RESULTS_PER_PAGE))
//            {
//                nextURL = baseURL + "&page=" + (page + 1);
//            }
//        	if (page > 0)
//            {
//                prevURL = baseURL + "&page=" + (page - 1);
//            }
//
//			results.setSimplePagination(resultCount,firstIndex,lastIndex,prevURL, nextURL);
//		}
//
//		/* Set up a table with search results (if there are any). */
//		Table table = results.addTable("group-edit-search-eperson",epeople.length + 1, 1);
//		Row header = table.addRow(Row.ROLE_HEADER);
//		header.addCell().addContent(T_epeople_column1);
//		header.addCell().addContent(T_epeople_column2);
//		header.addCell().addContent(T_epeople_column3);
//		header.addCell().addContent(T_epeople_column4);
//
//		for (EPerson person : epeople)
//		{
//			String epersonID = String.valueOf(person.getID());
//			String fullName = person.getFullName();
//			String email = person.getEmail();
//			String url = contextPath+"/admin/epeople?administrative-continue="+knot.getId()+"&submit_edit_eperson&epersonID="+epersonID;
//
//
//
//			Row personData = table.addRow();
//
//			personData.addCell().addContent(person.getID());
//			personData.addCell().addXref(url, fullName);
//			personData.addCell().addXref(url, email);
//
//			// check if they are already a member of the group
//			if (memberEPeopleIDs.contains(person.getID()))
//			{
//				// Check if they really members or just pending members
//				if (group != null && group.isMember(person))
//                {
//                    personData.addCellContent(T_member);
//                }
//				else
//                {
//                    personData.addCell().addHighlight("warn").addContent(T_pending);
//                }
//			}
//			else
//			{
//				personData.addCell().addButton("submit_add_eperson_"+epersonID).setValue(T_submit_add);
//			}
//		}
//
//		if (epeople.length <= 0) {
//			table.addRow().addCell(1, 4).addContent(T_no_results);
//		}
//	}
//
//
//	/**
//	 * Search for groups to add to this group.
//	 */
//	private void addGroupSearch(Division div, Group sourceGroup, String query, int page, Group parent, List<Integer> memberGroupIDs) throws WingException, SQLException
//	{
//		int resultCount = Group.searchResultCount(context, query);
//        Group[] groups = Group.search(context, query, page*RESULTS_PER_PAGE, RESULTS_PER_PAGE);
//
//		Division results = div.addDivision("results");
//
//		if (resultCount > RESULTS_PER_PAGE)
//		{
//        	// If there are enough results then paginate the results
//        	String baseURL = contextPath +"/admin/groups?administrative-continue="+knot.getId();
//        	int firstIndex = page*RESULTS_PER_PAGE+1;
//        	int lastIndex = page*RESULTS_PER_PAGE + groups.length;
//
//        	String nextURL = null, prevURL = null;
//        	if (page < (resultCount / RESULTS_PER_PAGE))
//            {
//                nextURL = baseURL + "&page=" + (page + 1);
//            }
//        	if (page > 0)
//            {
//                prevURL = baseURL + "&page=" + (page - 1);
//            }
//
//			results.setSimplePagination(resultCount,firstIndex,lastIndex,prevURL, nextURL);
//		}
//
//        Table table = results.addTable("roup-edit-search-group",groups.length + 1, 1);
//        Row header = table.addRow(Row.ROLE_HEADER);
//        header.addCell().addContent(T_groups_column1);
//        header.addCell().addContent(T_groups_column2);
//        header.addCell().addContent(T_groups_column3);
//        header.addCell().addContent(T_groups_column4);
//        header.addCell().addContent(T_groups_column5);
//
//        for (Group group : groups)
//        {
//        	String groupID = String.valueOf(group.getID());
//        	String name = group.getName();
//        	String url = contextPath+"/admin/groups?administrative-continue="+knot.getId()+"&submit_edit_group&groupID="+groupID;
//        	int memberCount = group.getMembers().length + group.getMemberGroups().length;
//
//        	Row row = table.addRow();
//
//        	row.addCell().addContent(groupID);
//        	if (AuthorizeManager.isAdmin(context))
//        		// Only administrators can edit other groups.
//            {
//                row.addCell().addXref(url, name);
//            }
//        	else
//            {
//                row.addCell().addContent(name);
//            }
//
//
//
//        	row.addCell().addContent(memberCount == 0 ? "-" : String.valueOf(memberCount));
//
//        	Cell cell = row.addCell();
//        	if (FlowGroupUtils.getCollectionId(group.getName()) > -1)
//        	{
//        		Collection collection = Collection.find(context, FlowGroupUtils.getCollectionId(group.getName()) );
//        		if (collection != null)
//        		{
//	        		String collectionName = collection.getMetadata("name");
//
//	        		if (collectionName == null)
//                    {
//                        collectionName = "";
//                    }
//	        		else if (collectionName.length() > MAX_COLLECTION_NAME)
//                    {
//                        collectionName = collectionName.substring(0, MAX_COLLECTION_NAME - 3) + "...";
//                    }
//
//	        		cell.addContent(collectionName+" ");
//
//	        		Highlight highlight = cell.addHighlight("fade");
//	        		highlight.addContent("[");
//	        		highlight.addXref(contextPath+"/handle/"+collection.getHandle(), T_groups_collection_link);
//	        		highlight.addContent("]");
//        		}
//        	}
//
//
//        	// Check if the group is already a member or would create a cycle.
//        	if (memberGroupIDs.contains(group.getID()))
//        	{
//        		// Check if they really members or just pending members
//				if (parent != null && parent.isMember(group))
//                {
//                    row.addCellContent(T_member);
//                }
//				else
//                {
//                    row.addCell().addHighlight("warn").addContent(T_pending);
//                }
//        	}
//        	else if (isDescendant(sourceGroup, group, memberGroupIDs))
//        	{
//        		row.addCellContent(T_cycle);
//	        }
//	        else
//	        {
//	        	row.addCell().addButton("submit_add_group_"+groupID).setValue(T_submit_add);
//	        }
//
//        }
//        if (groups.length <= 0) {
//			table.addRow().addCell(1, 4).addContent(T_no_results);
//		}
//	}

//
//	/**
//	 * Method to extensively check whether the first group has the second group as a distant
//	 * parent. This is used to avoid creating cycles like A->B, B->C, C->D, D->A which leads
//	 * all the groups involved to essentially include themselves.
//	 */
//	private boolean isDescendant(Group descendant, Group ancestor, List<Integer> memberGroupIDs) throws SQLException
//	{
//		Queue<Group> toVisit = new LinkedList<Group>();
//		Group currentGroup;
//
//		toVisit.offer(ancestor);
//
//		// Initialize by adding a list of our current list of group members.
//		for (Integer groupid : memberGroupIDs)
//		{
//			Group member = Group.find(context,groupid);
//			toVisit.offer(member);
//		}
//
//		while (!toVisit.isEmpty()) {
//			// 1. Grab a group from the queue
//			currentGroup = toVisit.poll();
//
//			// 2. See if it's the descendant we're looking for
//			if (currentGroup.equals(descendant)) {
//                return true;
//            }
//
//			// 3. If not, add that group's children to the queue
//			for (Group nextBatch : currentGroup.getMemberGroups()) {
//				toVisit.offer(nextBatch);
//			}
//		}
//		return false;
//	}
//
//

	/**
	 * Add a table with all the current department's member collections to the specified division.
	 * @throws SQLException
	 */
	private boolean addMemberList(Division div, EtdUnit parent, List<Integer> memberCollectionIDs, int highlightCollectionID) throws WingException, SQLException
	{
		// Flag to remember if there are any pending changes.
        boolean changes = false;

        Division members = div.addDivision("deparment-edit-members");
        members.setHead(T_members_head);

        Table table = members.addTable("department-edit-members-table",memberCollectionIDs.size() + 1, 3);

        Row header = table.addRow(Row.ROLE_HEADER);
        header.addCell().addContent(T_members_column1);
        header.addCell().addContent(T_members_column2);
        header.addCell().addContent(T_members_column3);
//        header.addCell().addContent(T_members_column4);

        // get all member collections, pend or actual
        @SuppressWarnings("unchecked") // the cast is correct
        List<Integer> allMemberCollectionIDs = new ArrayList<Integer>(memberCollectionIDs);
        for (Collection collection : parent.getCollections())
        {
        	if (!allMemberCollectionIDs.contains(collection.getID()))
            {
        		allMemberCollectionIDs.add(collection.getID());
            }
        }
        // Sort them to a consistent ordering
        Collections.sort(allMemberCollectionIDs);

        for (Integer collectionID : allMemberCollectionIDs)
        {
        	Collection collection = Collection.find(context, collectionID);
        	boolean highlight = (collection.getID() == highlightCollectionID);
        	boolean pendingAddition = !parent.isMember(collection);
        	boolean pendingRemoval = !memberCollectionIDs.contains(collectionID);
        	addMemberRow(table,collection,highlight,pendingAddition,pendingRemoval);

        	if (pendingAddition || pendingRemoval)
            {
                changes = true;
            }
        }

//        // get all group members, pend or actual
//        @SuppressWarnings("unchecked") // the cast is correct
//        List<Integer> allMemberGroupIDs = new ArrayList<Integer>(memberCollectionIDs);
//        for (Group group : parent.getMemberGroups())
//        {
//        	if (!allMemberGroupIDs.contains(group.getID()))
//            {
//                allMemberGroupIDs.add(group.getID());
//            }
//        }
//        // Sort them to a consistent ordering
//        Collections.sort(allMemberGroupIDs);
//
//        // Loop through all group ids and display them.
//        for (Integer groupID : allMemberGroupIDs)
//        {
//        	Group group = Group.find(context,groupID);
//        	boolean highlight = (group.getID() == highlightGroupID);
//        	boolean pendingAddition = !parent.isMember(group);
//        	boolean pendingRemoval = !memberCollectionIDs.contains(groupID);
//        	addMemberRow(table, group, highlight,pendingAddition,pendingRemoval);
//
//        	if (pendingAddition || pendingRemoval)
//            {
//                changes = true;
//            }
//        }
//
//
//        // get all members, pend or actual
//        @SuppressWarnings("unchecked") // the cast is correct
//        List<Integer> allMemberEPeopleIDs = new ArrayList<Integer>(memberEPeopleIDs);
//        for (EPerson eperson : parent.getMembers())
//        {
//        	if (!allMemberEPeopleIDs.contains(eperson.getID()))
//            {
//                allMemberEPeopleIDs.add(eperson.getID());
//            }
//        }
//        // Sort them to a consistent ordering
//        Collections.sort(allMemberEPeopleIDs);
//
//        for (Integer epersonID : allMemberEPeopleIDs)
//        {
//        	EPerson eperson = EPerson.find(context, epersonID);
//        	boolean highlight = (eperson.getID() == highlightEPersonID);
//        	boolean pendingAddition = !parent.isMember(eperson);
//        	boolean pendingRemoval = !memberEPeopleIDs.contains(epersonID);
//        	addMemberRow(table,eperson,highlight,pendingAddition,pendingRemoval);
//
//        	if (pendingAddition || pendingRemoval)
//            {
//                changes = true;
//            }
//        }

        if (allMemberCollectionIDs.size() <= 0)
        {
        	table.addRow().addCell(1, 4).addContent(T_members_none);
        }

        return changes;
	}

	/**
	 * Add a single member row for collections.
	 *
	 * @param table The table to add a row too.
	 * @param collection The collection being displayed
	 * @param highlight Should this collection be highlighted?
	 * @param pendingAddition Is this collection pending addition?
	 * @param pendingRemoval Is this collection pending removal?
	 */
	private void addMemberRow(Table table, Collection collection, boolean highlight, boolean pendingAddition, boolean pendingRemoval) throws WingException, SQLException
	{
    	String fullName = collection.getName();
//    	String email = collection.getEmail();
    	String url = contextPath+"/admin/departments?administrative-continue="+knot.getId()+"&submit_edit_collection&collectionID="+collection.getID();

    	Row collectionData = table.addRow(null,null,highlight ? "highlight" : null);

    	collectionData.addCell().addContent(collection.getID());

		Cell nameCell = collectionData.addCell();
		nameCell.addXref(url, fullName);
		if (pendingAddition)
		{
			nameCell.addContent(" ");
    		nameCell.addHighlight("warn").addContent(T_members_pending);
		}

//		collectionData.addCell().addXref(url, email);

		if (pendingRemoval)
        {
            collectionData.addCell().addHighlight("warn").addContent(T_pending);
        }
		else
        {
            collectionData.addCell().addButton("submit_remove_collection_" + collection.getID()).setValue(T_submit_remove);
        }
	}

//	/**
//	 * Add a single member row for groups.
//	 *
//	 * @param table The table to add the row too.
//	 * @param group The group being displayed in this row.
//	 * @param highlight Should the row be highlighted.
//	 * @param pendingAddition Is this group pending addition
//	 * @param pendingRemoval Is this group pending removal
//	 */
//	private void addMemberRow(Table table,Group group, boolean highlight, boolean pendingAddition, boolean pendingRemoval) throws WingException, SQLException
//	{
//    	String name = group.getName();
//    	String url = contextPath+"/admin/groups?administrative-continue="+knot.getId()+"&submit_edit_group&groupID="+group.getID();
//
//    	Row groupData = table.addRow(null,null,highlight ? "highlight" : null);
//
//    	groupData.addCell().addHighlight("bold").addContent(group.getID());
//
//    	// Mark if this member is pending or not.
//    	Cell nameCell = groupData.addCell();
//    	if (AuthorizeManager.isAdmin(context))
//        {
//            nameCell.addHighlight("bold").addXref(url, T_members_group_name.parameterize(name));
//        }
//    	else
//        {
//            nameCell.addHighlight("bold").addContent(T_members_group_name.parameterize(name));
//        }
//
//    	if (pendingAddition)
//    	{
//    		nameCell.addContent(" ");
//    		nameCell.addHighlight("warn").addContent(T_members_pending);
//    	}
//
//    	groupData.addCell().addContent("-");
//
//    	if (pendingRemoval)
//        {
//            groupData.addCell().addHighlight("warn").addContent(T_pending);
//        }
//    	else
//        {
//            groupData.addCell().addButton("submit_remove_group_" + group.getID()).setValue(T_submit_remove);
//        }
//	}
//
//	/**
//	 * Add a single member row for epeople.
//	 *
//	 * @param table The table to add a row too.
//	 * @param eperson The eperson being displayed
//	 * @param highlight Should this eperson be highlighted?
//	 * @param pendingAddition Is this eperson pending addition?
//	 * @param pendingRemoval Is this eperson pending removal?
//	 */
//	private void addMemberRow(Table table, EPerson eperson, boolean highlight, boolean pendingAddition, boolean pendingRemoval) throws WingException, SQLException
//	{
//    	String fullName = eperson.getFullName();
//    	String email = eperson.getEmail();
//    	String url = contextPath+"/admin/epeople?administrative-continue="+knot.getId()+"&submit_edit_eperson&epersonID="+eperson.getID();
//
//
//    	Row personData = table.addRow(null,null,highlight ? "highlight" : null);
//
//    	personData.addCell().addContent(eperson.getID());
//
//		Cell nameCell = personData.addCell();
//		nameCell.addXref(url, fullName);
//		if (pendingAddition)
//		{
//			nameCell.addContent(" ");
//    		nameCell.addHighlight("warn").addContent(T_members_pending);
//		}
//
//		personData.addCell().addXref(url, email);
//
//		if (pendingRemoval)
//        {
//            personData.addCell().addHighlight("warn").addContent(T_pending);
//        }
//		else
//        {
//            personData.addCell().addButton("submit_remove_eperson_" + eperson.getID()).setValue(T_submit_remove);
//        }
//	}
}