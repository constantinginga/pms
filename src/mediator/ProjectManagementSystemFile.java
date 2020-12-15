package mediator;

import model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import parser.XmlJsonParser;

import javax.naming.Name;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class ProjectManagementSystemFile
{
  private XmlJsonParser parser = new XmlJsonParser();
  private String fileName;
  private File fileForTeamMembers;
  private File fileForProjects;
  private String fileNameForProjects;
  private TeamMemberList teamMemberList;
  private String fileNameForTeamMembers;

  public ProjectManagementSystemFile(TeamMemberList teamMemberList)
  {
  }

  public void saveToFile(ProjectList projectList)
  {
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.newDocument();
      Element main = doc.createElement("ProjectList");
      for (Project project : projectList.getProjectList())
      {

        Element projectroot = doc.createElement("Project");
        main.appendChild(projectroot);

        Element prjSubID = doc.createElement("ProjectID");
        prjSubID.appendChild(doc.createTextNode(project.getId()));
        projectroot.appendChild(prjSubID);

        Element prjTitle = doc.createElement("ProjectTitle");
        prjTitle.appendChild(doc.createTextNode(project.getTitle()));
        projectroot.appendChild(prjTitle);

        Element prjstatus = doc.createElement("ProjectStatus");
        prjstatus.appendChild(doc.createTextNode(project.getStatus()));
        projectroot.appendChild(prjstatus);

        Element prjNotes = doc.createElement("ProjectNotes");
        prjNotes.appendChild(doc.createTextNode(project.getNote()));
        projectroot.appendChild(prjNotes);

        Element projectMembers = doc.createElement("ProjectMembers");

        Element prjprojectCreator = doc.createElement("ProjectCreator");

        Element prjprojectCreatorName = doc.createElement("projectCreatorName");
        prjprojectCreatorName.appendChild(
            doc.createTextNode(project.getProjectCreator().getName()));
        prjprojectCreator.appendChild(prjprojectCreatorName);

        Element prjprojectCreatorID = doc.createElement("projectCreatorID");
        prjprojectCreatorID.appendChild(
            doc.createTextNode(project.getProjectCreator().getId()));
        prjprojectCreator.appendChild(prjprojectCreatorID);
        projectMembers.appendChild(prjprojectCreator);

        Element prjScrumMaster = doc.createElement("ScrumMaster");

        Element prjScrumMasterName = doc.createElement("scrumMastersName");
        prjScrumMasterName.appendChild(
            doc.createTextNode(project.getScrumMaster().getName()));
        prjScrumMaster.appendChild(prjScrumMasterName);

        Element prjScrumMasterNameID = doc.createElement("scrumMastersID");
        prjScrumMasterNameID
            .appendChild(doc.createTextNode(project.getScrumMaster().getId()));
        prjScrumMaster.appendChild(prjScrumMasterNameID);
        projectMembers.appendChild(prjScrumMaster);

        Element prjproductOwner = doc.createElement("ProductOwner");

        Element prjproductOwnerName = doc.createElement("productOwnerName");
        prjproductOwnerName.appendChild(
            doc.createTextNode(project.getProductOwner().getName()));
        prjproductOwner.appendChild(prjproductOwnerName);

        Element prjproductOwnerID = doc.createElement("productOwnerID");
        prjproductOwnerID
            .appendChild(doc.createTextNode(project.getProductOwner().getId()));
        prjproductOwner.appendChild(prjproductOwnerID);
        projectMembers.appendChild(prjproductOwner);

        Element memberList = doc.createElement("TeamMemberListForPorjects");
        for (TeamMember member : project.getMembers().getTeamMembers())
        {
          Element members = doc.createElement("Member");

          Element subElement = doc.createElement("TeamMembersName");
          subElement.appendChild(doc.createTextNode(member.getName()));
          members.appendChild(subElement);

          subElement = doc.createElement("TeamMembersID");
          subElement.appendChild(doc.createTextNode(member.getId()));
          members.appendChild(subElement);
          memberList.appendChild(members);
        }
        projectMembers.appendChild(memberList);
        projectroot.appendChild(projectMembers);

        Element requimentList = doc.createElement("requirementList");
        for (Requirement requirement : project.getRequirementList()
            .getRequirementList())
        {
          Element reqroot = doc.createElement("Requirement");
          Element reqAtributes = doc.createElement("RequirementAtributes");

          Element reqSubID = doc.createElement("RequirementID");
          reqSubID.appendChild(doc.createTextNode(requirement.getId()));
          reqAtributes.appendChild(reqSubID);

          Element reqTitle = doc.createElement("RequirementUserStory");
          reqTitle.appendChild(doc.createTextNode(requirement.getUserStory()));
          reqAtributes.appendChild(reqTitle);

          Element reqstatus = doc.createElement("RequirementStatus");
          reqstatus.appendChild(doc.createTextNode(requirement.getStatus()));
          reqAtributes.appendChild(reqstatus);

          Element isFunctional = doc.createElement("IsFunctional");
          isFunctional.appendChild(doc.createTextNode(String.valueOf(requirement.isFunctional())));
          reqAtributes.appendChild(isFunctional);

          reqroot.appendChild(reqAtributes);

          Element reqResPerson = doc.createElement("reqResPerson");

          Element reqResPersonName = doc
              .createElement("reqResponsiblePersonName");
          reqResPersonName.appendChild(
              doc.createTextNode(requirement.getResponsiblePerson().getName()));
          reqResPerson.appendChild(reqResPersonName);

          Element reqResPersonID = doc.createElement("reqResponsiblePersonID");
          reqResPersonID.appendChild(
              doc.createTextNode(requirement.getResponsiblePerson().getId()));
          reqResPerson.appendChild(reqResPersonID);
          reqroot.appendChild(reqResPerson);

          Element estime = doc.createElement("estimatedTime");
          estime.appendChild(doc.createTextNode(
              String.valueOf(requirement.getEstimatedTime())));
          reqroot.appendChild(estime);

          Element acctualtime = doc.createElement("actualTime");
          acctualtime.appendChild(
              doc.createTextNode(String.valueOf(requirement.getActualTime())));
          reqroot.appendChild(acctualtime);

          Element deadline = doc.createElement("DeadLine");

          Element day = doc.createElement("Day");
          day.appendChild(doc.createTextNode(
              String.valueOf(requirement.getDeadline().getDay())));
          deadline.appendChild(day);
          Element month = doc.createElement("Month");
          month.appendChild(doc.createTextNode(
              String.valueOf(requirement.getDeadline().getMonth())));
          deadline.appendChild(month);
          Element year = doc.createElement("Year");
          year.appendChild(doc.createTextNode(
              String.valueOf(requirement.getDeadline().getYear())));
          deadline.appendChild(year);
          reqroot.appendChild(deadline);

          memberList = doc.createElement("TeamMemberListForReq");
          for (TeamMember member : requirement.getMembers().getTeamMembers())
          {
            Element members = doc.createElement("Member");

            Element subElement = doc.createElement("TeamMembersName");
            subElement.appendChild(doc.createTextNode(member.getName()));
            members.appendChild(subElement);

            subElement = doc.createElement("TeamMembersID");
            subElement.appendChild(doc.createTextNode(member.getId()));
            members.appendChild(subElement);
            memberList.appendChild(members);
          }
          reqroot.appendChild(memberList);

          Element TaskList = doc.createElement("TaskList");
          for (Task task : project.getRequirement(requirement.getId())
              .getTaskList().getTaskList())
          {
            Element taskroot = doc.createElement("Task");

            Element taskSubID = doc.createElement("TaskID");
            taskSubID.appendChild(doc.createTextNode(task.getId()));
            taskroot.appendChild(taskSubID);

            Element taskTitle = doc.createElement("TaskTitle");
            taskTitle.appendChild(doc.createTextNode(task.getTitle()));
            taskroot.appendChild(taskTitle);

            Element taskstatus = doc.createElement("TaskStatus");
            taskstatus.appendChild(doc.createTextNode(task.getStatus()));
            taskroot.appendChild(taskstatus);

            Element taskResPerson = doc.createElement("TaskReqPerson");

            Element taskResPersonName = doc
                .createElement("taskResponsiblePersonName");
            taskResPersonName.appendChild(
                doc.createTextNode(task.getResponsiblePerson().getName()));
            taskResPerson.appendChild(taskResPersonName);

            Element taskResPersonID = doc
                .createElement("taskResponsiblePersonID");
            taskResPersonID.appendChild(
                doc.createTextNode(task.getResponsiblePerson().getId()));
            taskResPerson.appendChild(taskResPersonID);
            taskroot.appendChild(taskResPerson);

            estime = doc.createElement("estimatedTime");
            estime.appendChild(
                doc.createTextNode(String.valueOf(task.getEstimatedTime())));
            taskroot.appendChild(estime);

            acctualtime = doc.createElement("actualTime");
            acctualtime.appendChild(
                doc.createTextNode(String.valueOf(task.getActualTime())));
            taskroot.appendChild(acctualtime);

            deadline = doc.createElement("DeadLine");

            day = doc.createElement("Day");
            day.appendChild(doc.createTextNode(
                String.valueOf(task.getDeadline().getDay())));
            deadline.appendChild(day);
            month = doc.createElement("Month");
            month.appendChild(doc.createTextNode(
                String.valueOf(task.getDeadline().getMonth())));
            deadline.appendChild(month);
            year = doc.createElement("Year");
            year.appendChild(doc.createTextNode(
                String.valueOf(task.getDeadline().getYear())));
            deadline.appendChild(year);
            taskroot.appendChild(deadline);

            memberList = doc.createElement("TeamMemberListForTaks");
            for (TeamMember member : task.getMembers().getTeamMembers())
            {
              Element members = doc.createElement("Member");

              Element subElement = doc.createElement("TeamMembersName");
              subElement.appendChild(doc.createTextNode(member.getName()));
              members.appendChild(subElement);

              subElement = doc.createElement("TeamMembersID");
              subElement.appendChild(doc.createTextNode(member.getId()));
              members.appendChild(subElement);
              memberList.appendChild(members);
            }
            taskroot.appendChild(memberList);
            TaskList.appendChild(taskroot);
          }
          reqroot.appendChild(TaskList);
          requimentList.appendChild(reqroot);
        }
        projectroot.appendChild(requimentList);


        main.appendChild(projectroot);
      }
      doc.appendChild(main);
      Transformer transformer = TransformerFactory.newInstance()
          .newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer
          .setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      File file = new File("pmsFiles.xml");
      transformer.transform(new DOMSource(doc), new StreamResult(file));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void saveToFile(TeamMemberList teamMemberList)
  {
    try
    {

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.newDocument();
      Element main = doc.createElement("TeamMemberList");
      for (TeamMember member : teamMemberList.getTeamMembers())
      {
        Element root = doc.createElement("Member");

        Element subElement = doc.createElement("TeamMembersName");
        subElement.appendChild(doc.createTextNode(member.getName()));
        root.appendChild(subElement);

        subElement = doc.createElement("TeamMembersID");
        subElement.appendChild(doc.createTextNode(member.getId()));
        root.appendChild(subElement);
        main.appendChild(root);
      }
      Element root = doc.createElement("idIndex");
      root.appendChild(
          doc.createTextNode(String.valueOf(teamMemberList.getIdIndex())));
      main.appendChild(root);
      doc.appendChild(main);
      Transformer transformer = TransformerFactory.newInstance()
          .newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer
          .setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      File file = new File("pmsTeamMembers.xml");
      transformer.transform(new DOMSource(doc), new StreamResult(file));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public TeamMemberList readTeamMemberList()
  {
    TeamMemberList teamMembersReading = new TeamMemberList();

    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse("pmsTeamMembers.xml");
      Transformer transformer = TransformerFactory.newInstance()
          .newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "no");
      NodeList rootList = doc.getElementsByTagName("TeamMemberList");
      Node rootNode = rootList.item(0);
      NodeList subNodes = rootNode.getChildNodes();
      ArrayList<String> ids = new ArrayList<>();
      ArrayList<String> Names = new ArrayList<>();
      for (int i = 0; i < subNodes.getLength(); i++)
      {
        if (subNodes.item(i).getNodeName().equals("Member"))
        {
          NodeList subSubNotes = subNodes.item(i).getChildNodes();
          for (int e = 0; e < subSubNotes.getLength(); e++)
          {
            if (subSubNotes.item(e).getNodeName().equals("TeamMembersName"))
            {
              Names.add(subSubNotes.item(e).getTextContent());
            }
            else if (subSubNotes.item(e).getNodeName().equals("TeamMembersID"))
            {
              ids.add(subSubNotes.item(e).getTextContent());
            }
          }
        }
        else if (subNodes.item(i).getNodeName().equals("idIndex"))
        {
          teamMembersReading.setIdIndex(Integer.parseInt(subNodes.item(i).getTextContent()));
        }
      }
      for (int i = 0; i < Names.size(); i++)
      {
        TeamMember Bob = new TeamMember(Names.get(i));
        Bob.setId(ids.get(i));
        teamMembersReading.addAlreadyExists(Bob);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return teamMembersReading;

  }

  public ProjectList readProjects()
  {
    ProjectList projectList = new ProjectList();
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse("pmsFiles.xml");
      Transformer transformer = TransformerFactory.newInstance()
          .newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "no");
      NodeList rootList = doc.getElementsByTagName("ProjectList");
      Node rootNode = rootList.item(0);
      NodeList subNodes = rootNode.getChildNodes();
      if (subNodes.getLength() == 0)
      {
        return new ProjectList();
      }
      for (int i = 0; i < subNodes.getLength(); i++)
      {
        if (subNodes.item(i).getNodeName().equals("Project"))
        {
          NodeList subSubNotes = subNodes.item(i).getChildNodes();
          Project project = new Project("TEMPValueForAProject", "Not Started");
          for (int e = 0; e < subSubNotes.getLength(); ++e)
          {
            if (subSubNotes.item(e).getNodeName().equals("ProjectID"))
            {
              project.setId(subSubNotes.item(e).getTextContent());
            }
            else if (subSubNotes.item(e).getNodeName().equals("ProjectTitle"))
            {
              project.set(subSubNotes.item(e).getTextContent());
            }
            else if (subSubNotes.item(e).getNodeName().equals("ProjectStatus"))
            {
              project.setStatusForProject(subSubNotes.item(e).getTextContent());
            }
            else if (subSubNotes.item(e).getNodeName().equals("ProjectNotes"))
            {
              project.setNote(subSubNotes.item(e).getTextContent());
            }
            else if (subSubNotes.item(e).getNodeName().equals("ProjectMembers"))
            {
              NodeList memberi = subSubNotes.item(e).getChildNodes();
              if (memberi.getLength() > 0)
              {
                for (int o = 0; o < memberi.getLength(); o++)
                {
                  if (memberi.item(o).getNodeName().equals("ProjectCreator"))
                  {
                    NodeList listForStaff = memberi.item(o).getChildNodes();
                    TeamMember staff = new TeamMember("Kludis");
                    for (int x = 0; x < listForStaff.getLength(); x++)
                    {
                      if (listForStaff.item(x).getNodeName().equals("projectCreatorName"))
                      {
                        staff.setName(listForStaff.item(x).getTextContent());
                      }
                      else if (listForStaff.item(x).getNodeName().equals("projectCreatorID"))
                      {
                        staff.setId(listForStaff.item(x).getTextContent());
                      }
                    }
                    project.setProjectCreator(staff);
                  }
                  else if (memberi.item(o).getNodeName().equals("ScrumMaster"))
                  {
                    NodeList listForStaff = memberi.item(o).getChildNodes();
                    TeamMember staff = new TeamMember("Kludis");
                    for (int x = 0; x < listForStaff.getLength(); x++)
                    {
                      if (listForStaff.item(x).getNodeName().equals("scrumMastersName"))
                      {
                        staff.setName(listForStaff.item(x).getTextContent());
                      }
                      else if (listForStaff.item(x).getNodeName().equals("scrumMastersID"))
                      {
                        staff.setId(listForStaff.item(x).getTextContent());
                      }
                    }
                    project.setScrumMaster(staff);
                  }
                  else if (memberi.item(o).getNodeName().equals("ProductOwner"))
                  {
                    NodeList listForStaff = memberi.item(o).getChildNodes();
                    TeamMember staff = new TeamMember("Kludis");
                    for (int x = 0; x < listForStaff.getLength(); x++)
                    {
                      if (listForStaff.item(x).getNodeName().equals("productOwnerName"))
                      {
                        staff.setName(listForStaff.item(x).getTextContent());
                      }
                      else if (listForStaff.item(x).getNodeName().equals("productOwnerID"))
                      {
                        staff.setId(listForStaff.item(x).getTextContent());
                      }
                    }
                    project.setProductOwner(staff);
                  }
                  else if (memberi.item(o).getNodeName().equals("TeamMemberListForPorjects"))
                  {
                    System.out.println(memberi.item(o).getNodeName());
                    NodeList memberList = memberi.item(o).getChildNodes();
                    ArrayList<String> ids = new ArrayList<>();
                    ArrayList<String> Names = new ArrayList<>();
                    for (int l = 0; l < memberList.getLength(); l++)
                    {
                      if (memberList.item(l).getNodeName().equals("Member"))
                      {
                        NodeList subsSubNotes = memberList.item(l).getChildNodes();
                        for (int el = 0; el < subsSubNotes.getLength(); el++)
                        {
                          if (subsSubNotes.item(el).getNodeName().equals("TeamMembersName"))
                          {
                            Names.add(subsSubNotes.item(el).getTextContent());
                          }
                          else if (subsSubNotes.item(el).getNodeName().equals("TeamMembersID"))
                          {
                            ids.add(subsSubNotes.item(el).getTextContent());
                          }
                        }
                      }
                    }
                    for (int ix = 0; ix < Names.size(); ix++)
                    {
                      TeamMember Bob = new TeamMember(Names.get(ix));
                      Bob.setId(ids.get(ix));
                      project.addAlreadyExistsTeamMember(Bob);
                    }
                  }
                }
              }
            }
            else if (subSubNotes.item(e).getNodeName().equals("requirementList"))
            {
              System.out.println(subSubNotes.item(e).getNodeName());

              NodeList reqList = subSubNotes.item(e).getChildNodes();
              if (reqList.getLength() > 0)
              {
                for (int z = 0; z < reqList.getLength(); z++)
                {
                  if (reqList.item(z).getNodeName().equals("Requirement"))
                  {
                    NodeList req = reqList.item(z).getChildNodes();
                    Requirement requirement = new Requirement("jasjdasd", new TeamMember("bob"), "Not Started", 3234,
                        new MyDate(13, 12, 3000),false);
                    for (int v = 0; v < req.getLength(); v++)
                    {
                      if (req.item(v).getNodeName()
                          .equals("RequirementAtributes"))
                      {
                        if (req.item(v).hasChildNodes()){
                          NodeList reqAttributes = req.item(v).getChildNodes();
                       for (int k = 0; k< reqAttributes.getLength(); k++){

                         if (req.item(k).getNodeName().equals("RequirementID"))
                         {
                           requirement.setId(req.item(k).getTextContent());
                         }
                         else if (req.item(k).getNodeName().equals("RequirementUserStory"))
                         {
                           requirement.setTitle(req.item(k).getTextContent());
                         }
                         else if (req.item(k).getNodeName().equals("RequirementStatus"))
                         {
                           requirement.setStatus(req.item(k).getTextContent());
                         }
                         else if (req.item(k).getNodeName().equals("IsFunctional")){
                           requirement.setFunctional(Boolean.getBoolean(req.item(k).getTextContent()));
                         }
                       }
                        }
                      }

                      else if (req.item(v).getNodeName().equals("estimatedTime"))
                      {
                        requirement.setEstimatedTime(Integer.parseInt(req.item(v).getTextContent()));
                      }
                      else if (req.item(v).getNodeName().equals("actualTime"))
                      {
                        requirement.setActualTime(Integer.parseInt(req.item(v).getTextContent()));
                      }
                      else if (req.item(v).getNodeName().equals("reqResPerson"))
                      {
                        NodeList listForStaff = req.item(v).getChildNodes();
                        TeamMember staff = new TeamMember("Kludis");
                        for (int xv = 0; xv < listForStaff.getLength(); xv++)
                        {
                          if (listForStaff.item(xv).getNodeName().equals("reqResponsiblePersonName"))
                          {
                            staff.setName(listForStaff.item(xv).getTextContent());
                          }
                          else if (listForStaff.item(xv).getNodeName().equals("reqResponsiblePersonID"))
                          {
                            staff.setId(listForStaff.item(xv).getTextContent());
                          }
                        }
                        requirement.setResponsiblePerson(staff);
                      }
                      else if (req.item(v).getNodeName().equals("DeadLine"))
                      {
                        NodeList deadline = req.item(v).getChildNodes();
                        int day = 0;
                        int month = 0;
                        int year = 0;
                        for (int xv = 0; xv < deadline.getLength(); xv++)
                        {
                          if (deadline.item(xv).getNodeName().equals("Day"))
                          {
                            day = Integer.parseInt(deadline.item(xv).getTextContent());
                          }
                          if (deadline.item(xv).getNodeName().equals("Month"))
                          {
                            month = Integer.parseInt(deadline.item(xv).getTextContent());
                          }
                          if (deadline.item(xv).getNodeName().equals("Year"))
                          {
                            year = Integer.parseInt(deadline.item(xv).getTextContent());
                          }
                        }
                        MyDate deadlineja = new MyDate(day, month, year);
                        requirement.setDeadline(deadlineja);
                      }
                      else if (req.item(v).getNodeName().equals("TeamMemberListForReq"))
                      {
                        NodeList memberList = req.item(v).getChildNodes();
                        ArrayList<String> ids = new ArrayList<>();
                        ArrayList<String> Names = new ArrayList<>();
                        for (int l = 0; l < memberList.getLength(); l++)
                        {
                          if (memberList.item(l).getNodeName().equals("Member"))
                          {
                            NodeList subsSubNotes = memberList.item(l).getChildNodes();
                            for (int el = 0; el < subsSubNotes.getLength(); el++)
                            {
                              if (subsSubNotes.item(el).getNodeName().equals("TeamMembersName"))
                              {
                                Names.add(subsSubNotes.item(el).getTextContent());
                              }
                              else if (subsSubNotes.item(el).getNodeName().equals("TeamMembersID"))
                              {
                                ids.add(subsSubNotes.item(el).getTextContent());
                              }
                            }
                          }
                        }
                        for (int ix = 0; ix < Names.size(); ix++)
                        {
                          TeamMember Bob = new TeamMember(Names.get(ix));
                          Bob.setId(ids.get(ix));
                          requirement.addAlreadyExistsTeamMember(Bob);
                        }
                      }
                      else if (req.item(v).getNodeName().equals("TaskList"))
                      {
                        if (req.item(v).hasChildNodes()){
                          NodeList taskList = req.item(v).getChildNodes();
                        {
                          for (int xv = 0; xv < reqList.getLength(); xv++)
                          {
                            if (taskList.item(xv).hasChildNodes())
                            {
                              if (taskList.item(xv).getNodeName().equals("Task"))
                              {
                                NodeList task = taskList.item(xv).getChildNodes();
                                Task taskk = new Task("TempTask", new TeamMember("bob"), 3234,
                                    new MyDate(13, 12, 3000));
                                for (int b = 0; b < task.getLength(); b++)
                                {

                                  if (task.item(b).getNodeName().equals("TaskID"))
                                  {
                                    taskk.setId(task.item(b).getTextContent());
                                  }
                                  else if (task.item(b).getNodeName().equals("TaskTitle"))
                                  {
                                    taskk.setTitle(task.item(b).getTextContent());
                                  }
                                  else if (task.item(b).getNodeName().equals("TaskStatus"))
                                  {
                                    taskk.setStatus(
                                        task.item(b).getTextContent());
                                  }
                                  else if (task.item(b).getNodeName().equals("estimatedTime"))
                                  {
                                    taskk.setEstimatedTime(Integer.parseInt(
                                        task.item(b).getTextContent()));
                                  }
                                  else if (task.item(b).getNodeName().equals("actualTime"))
                                  {
                                    taskk.setActualTime(Integer.parseInt(
                                        task.item(b).getTextContent()));
                                  }
                                  else if (task.item(b).getNodeName().equals("TaskReqPerson"))
                                  {
                                    NodeList listForStaff = task.item(b).getChildNodes();
                                    TeamMember staff = new TeamMember("Kludis");
                                    for (int xb = 0; xb < listForStaff.getLength(); xb++)
                                    {
                                      if (listForStaff.item(xb).getNodeName().equals("taskResponsiblePersonName"))
                                      {
                                        staff.setName(listForStaff.item(xb).getTextContent());
                                      }
                                      else if (listForStaff.item(xb).getNodeName()
                                          .equals("taskResponsiblePersonID"))
                                      {
                                        staff.setId(listForStaff.item(xb).getTextContent());
                                      }
                                    }
                                    taskk.setResponsiblePerson(staff);
                                  }
                                  else if (task.item(b).getNodeName().equals("DeadLine"))
                                  {
                                    NodeList deadline = task.item(b).getChildNodes();
                                    int day = 0;
                                    int month = 0;
                                    int year = 0;
                                    for (int xb = 0; xb < deadline.getLength(); xb++)
                                    {
                                      if (deadline.item(xb).getNodeName().equals("Day"))
                                      {
                                        day = Integer.parseInt(
                                            deadline.item(xb).getTextContent());
                                      }
                                      if (deadline.item(xb).getNodeName().equals("Month"))
                                      {
                                        month = Integer.parseInt(
                                            deadline.item(xb).getTextContent());
                                      }
                                      if (deadline.item(xb).getNodeName().equals("Year"))
                                      {
                                        year = Integer.parseInt(
                                            deadline.item(xb).getTextContent());
                                      }
                                    }
                                    MyDate deadlineja = new MyDate(day, month,
                                        year);
                                    taskk.setDeadline(deadlineja);
                                  }
                                  else if (task.item(b).getNodeName().equals("TeamMemberListForTaks"))
                                  {
                                    NodeList memberList = task.item(b).getChildNodes();
                                    ArrayList<String> ids = new ArrayList<>();
                                    ArrayList<String> Names = new ArrayList<>();
                                    for (int l = 0; l < memberList.getLength(); l++)
                                    {
                                      if (memberList.item(l).getNodeName().equals("Member"))
                                      {
                                        if (memberList.item(l).hasChildNodes())
                                          subSubNotes = memberList.item(l).getChildNodes();
                                        if (subSubNotes.getLength() > 0)
                                        {
                                          if (subSubNotes.getLength() > 0)
                                          {
                                            for (int el = 0; el < subSubNotes.getLength(); el++)
                                            {
                                              if (subSubNotes.item(el).getNodeName()
                                                  .equals("TeamMembersName"))
                                              {
                                                Names.add(subSubNotes.item(el).getTextContent());
                                              }
                                              else if (subSubNotes.item(el).getNodeName()
                                                  .equals("TeamMembersID"))
                                              {
                                                ids.add(subSubNotes.item(el).getTextContent());
                                              }
                                            }
                                          }
                                        }
                                        for (int ix = 0; ix < Names.size(); ix++)
                                        {
                                          TeamMember Bob = new TeamMember(Names.get(ix));
                                          Bob.setId(ids.get(ix));
                                          taskk.addAlreadyExistsTeamMember(Bob);
                                        }
                                      }
                                    }
                                  }
                                }
                                requirement.addTask(taskk);
                              }
                            }
                          }
                        }
                      }
                      }
                    }
                    project.addRequirement(requirement);
                  }
                }
              }
            }
          }
          projectList.add(project);
        }
      }

      return projectList;
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

    return null;
  }
}