<%--
  - edit-collection.jsp
  -
  - Version: $Revision: 1.1 $
  -
  - Date: $Date: 2004/12/13 16:59:43 $
  -
  - Copyright (c) 2002, Hewlett-Packard Company and Massachusetts
  - Institute of Technology.  All rights reserved.
  -
  - Redistribution and use in source and binary forms, with or without
  - modification, are permitted provided that the following conditions are
  - met:
  -
  - - Redistributions of source code must retain the above copyright
  - notice, this list of conditions and the following disclaimer.
  -
  - - Redistributions in binary form must reproduce the above copyright
  - notice, this list of conditions and the following disclaimer in the
  - documentation and/or other materials provided with the distribution.
  -
  - - Neither the name of the Hewlett-Packard Company nor the name of the
  - Massachusetts Institute of Technology nor the names of their
  - contributors may be used to endorse or promote products derived from
  - this software without specific prior written permission.
  -
  - THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
  - ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
  - LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
  - A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
  - HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
  - INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
  - BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
  - OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
  - ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
  - TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
  - USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
  - DAMAGE.
  --%>

<%--
  - Show form allowing edit of collection metadata
  -
  - Attributes:
  -    community    - community to create new collection in, if creating one
  -    collection   - collection to edit, if editing an existing one.  If this
  -                  is null, we are creating one.
  --%>

<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="org.dspace.app.webui.servlet.admin.EditCommunitiesServlet" %>
<%@ page import="org.dspace.content.Bitstream" %>
<%@ page import="org.dspace.content.Collection" %>
<%@ page import="org.dspace.content.Community" %>
<%@ page import="org.dspace.content.Item" %>
<%@ page import="org.dspace.eperson.Group" %>

<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>

<%
    Collection collection = (Collection) request.getAttribute("collection");
    Community community = (Community) request.getAttribute("community");
    Boolean admin_b = (Boolean)request.getAttribute("admin_button");
    boolean admin_button = (admin_b == null ? false : admin_b.booleanValue());
    
    String name = "";
    String shortDesc = "";
    String intro = "";
    String copy = "";
    String side = "";
    String license = "";
    String provenance = "";

    Group[] wfGroups = new Group[3];
    wfGroups[0] = null;
    wfGroups[1] = null;
    wfGroups[2] = null;

    Group admins     = null;
    Group submitters = null;

    Item template = null;

    Bitstream logo = null;
    
    if (collection != null)
    {
        name = collection.getMetadata("name");
        shortDesc = collection.getMetadata("short_description");
        intro = collection.getMetadata("introductory_text");
        copy = collection.getMetadata("copyright_text");
        side = collection.getMetadata("side_bar_text");
        provenance = collection.getMetadata("provenance_description");

        if (collection.hasCustomLicense())
        {
            license = collection.getLicense();
        }
        
        if (copy == null)
        {
            copy = "";
        }
        
        if (side == null)
        {
            side = "";
        }

        if (provenance == null)
        {
            provenance = "";
        }

        wfGroups[0] = collection.getWorkflowGroup(1);
        wfGroups[1] = collection.getWorkflowGroup(2);
        wfGroups[2] = collection.getWorkflowGroup(3);

        admins     = collection.getAdministrators();
        submitters = collection.getSubmitters();

        template = collection.getTemplateItem();

        logo = collection.getLogo();
    }
%>

<dspace:layout title="Edit Collection"
               navbar="admin"
               locbar="link"
               parentlink="/dspace-admin"
               parenttitle="Administer"
               nocache="true">

<%
    if (collection == null)
    {
%>
    <H1>Create Collection</H1>
<% } else { %>
    <H1>Edit Collection <%= collection.getHandle() %></H1>
    <% if(admin_button ) { %>
      <center>
        <table width="70%">
          <tr>
            <td class="standard">
              <form method=POST>
                <input type="hidden" name="action" value="<%= EditCommunitiesServlet.START_DELETE_COLLECTION %>">
                <input type="hidden" name="community_id" value="<%= community.getID() %>">
                <input type="hidden" name="collection_id" value="<%= collection.getID() %>">
                <input type="submit" name="submit" value="Delete this Collection...">
              </form>
            </td>
          </tr>
        </table>
      </center>
    <% } %>
<% } %>

    <form method=POST action="<%= request.getContextPath() %>/tools/edit-communities">
        <table>
<%-- ===========================================================
     Basic metadata
     =========================================================== --%>
            <tr>
                <td class="submitFormLabel">Name:</td>
                <td><input type="text" name="name" value="<%= name %>" size=50></td>
            </tr>
            <tr>
                <td class="submitFormLabel">Short Description</td>
                <td>
                    <input type="text" name="short_description" value="<%= shortDesc %>" size=50>
                </td>
            </tr>
            <tr>
                <td class="submitFormLabel">Introductory text (HTML):</td>
                <td>
                    <textarea name="introductory_text" rows=6 cols=50><%= intro %></textarea>
                </td>
            </tr>
            <tr>
                <td class="submitFormLabel">Copyright text (plain text):</td>
                <td>
                    <textarea name="copyright_text" rows=6 cols=50><%= copy %></textarea>
                </td>
            </tr>
            <tr>
                <td class="submitFormLabel">Side bar text (HTML):</td>
                <td>
                    <textarea name="side_bar_text" rows=6 cols=50><%= side %></textarea>
                </td>
            </tr>
            <tr>
                <td class="submitFormLabel">License:</td>
                <td>
                    <textarea name="license" rows=6 cols=50><%= license %></textarea>
                </td>
            </tr>
            <tr>
                <td class="submitFormLabel">Provenance:</td>
                <td>
                    <textarea name="provenance_description" rows=6 cols=50><%= provenance %></textarea>
                </td>
            </tr>
<%-- ===========================================================
     Logo
     =========================================================== --%>
            <tr>
                <td class="submitFormLabel">Logo:</td>
                <td>
<%  if (logo != null) { %>
                    <table>
                        <tr>
                            <td>
                                <img src="<%= request.getContextPath() %>/retrieve/<%= logo.getID() %>">
                            </td>
                            <td>
                                <input type="submit" name="submit_set_logo" value="Upload new logo..."><br><br>
                                <input type="submit" name="submit_delete_logo" value="Delete (no logo)">
                            </td>
                        </tr>
                    </table>
<%  } else { %>
                    <input type="submit" name="submit_set_logo" value="Upload a logo...">
<%  } %>
                </td>
            </tr>
            
            <tr><td>&nbsp;</td></tr>
            <tr><td colspan=2><center><h3>Submission Workflow</h3></center></td></tr>

<% if(admin_button ) { %>
<%-- ===========================================================
     Collection Submitters
     =========================================================== --%>
            <tr>
                <td class="submitFormLabel">Submitters:</td>
                <td>
<%  if (submitters == null) {%>
                    <input type="submit" name="submit_submitters_create" value="Create...">
<%  } else { %>
                    <input type="submit" name="submit_submitters_edit" value="Edit...">
<%  } %>                    
                </td>
            </tr>   
            
<%-- ===========================================================
     Workflow groups
     =========================================================== --%>
<%
    String[] roleTexts = {"Accept/Reject", "Accept/Reject/Edit Metadata", "Edit Metadata"};
    for (int i = 0; i<3; i++) { %>
            <tr>
                <td class="submitFormLabel"><em><%= roleTexts[i] %></em> Step:</td>
                <td>
<%      if (wfGroups[i] == null) { %>
                    <input type="submit" name="submit_wf_create_<%= i + 1 %>" value="Create...">
<%      } else { %>
                    <input type="submit" name="submit_wf_edit_<%= i + 1 %>" value="Edit...">
                    <input type="submit" name="submit_wf_delete_<%= i + 1 %>" value="Delete">
<%      } %>
                </td>
            </tr>
<%  } %>

            <tr><td>&nbsp;</td></tr>

<%-- ===========================================================
     Collection Administrators
     =========================================================== --%>
            <tr>
                <td class="submitFormLabel">Collection Administrators:</td>
                <td>
<%  if (admins == null) {%>
                    <input type="submit" name="submit_admins_create" value="Create...">
<%  } else { %>
                    <input type="submit" name="submit_admins_edit" value="Edit...">
<%  } %>                    
                </td>
            </tr>   
<%  } %>
<%-- ===========================================================
     Item template
     =========================================================== --%>
            <tr>
                <td class="submitFormLabel">Item template:</td>
                <td>
<%  if (template == null) {%>
                    <input type="submit" name="submit_create_template" value="Create...">
<%  } else { %>
                    <input type="submit" name="submit_edit_template" value="Edit...">
                    <input type="submit" name="submit_delete_template" value="Delete">
<%  } %>                    
                </td>
            </tr>   
<% if(admin_button ) { %>
<%-- ===========================================================
     Edit collection's policies
     =========================================================== --%>
            <tr>
                <td class="submitFormLabel">Collection's Authorizations:</td>
                <td>
                    <input type="submit" name="submit_authorization_edit" value="Edit...">
                </td>
            </tr>   
<%  } %>

        </table>
        
        <P>&nbsp;</P>

        <center>
            <table width="70%">
                <tr>
                    <td class="standard">
<%
    if (collection == null)
    {
%>
                        <input type="hidden" name="community_id" value="<%= community.getID() %>">
                        <input type="hidden" name="create" value="true">
                        <input type="submit" name="submit" value="Create">
<%
    }
    else
    {
%>
                        <input type="hidden" name="community_id" value="<%= community.getID() %>">
                        <input type="hidden" name="collection_id" value="<%= collection.getID() %>">
                        <input type="hidden" name="create" value="false">
                        <input type="submit" name="submit" value="Update">
<%
    }
%>
                    </td>
                    <td>
                        <input type="hidden" name="community_id" value="<%= community.getID() %>">
                        <input type="hidden" name="action" value="<%= EditCommunitiesServlet.CONFIRM_EDIT_COLLECTION %>">
                        <input type="submit" name="submit_cancel" value="Cancel">
                    </td>
                </tr>
            </table>
        </center>
    </form>
</dspace:layout>