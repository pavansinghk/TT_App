package model;


import java.util.Date;


import model.common.AppModule;

import oracle.adf.share.ADFContext;


import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.SequenceImpl;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Apr 11 11:58:41 IST 2017-------pavan change
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppModuleImpl extends ApplicationModuleImpl implements AppModule {
    /**
     * This is the default constructor (do not remove).
     */
    public AppModuleImpl() {       
    }
    
    /*to do insertion in 
     * 
     * 
     * 
     */  
    public String  CreateTicketingTool(Date closeddate,String assignedTo){
            String ticNumber = generateNumber();
            String userId = null ;
            try
                      {
                            ViewObject ticTVO = this.getTicketingToolTransVO1();
                            ViewObject ticVO = this.getTicketingtoolVO1();
                            ViewObject AttachVO = this.getAttachmentsVO1();
                            ViewObject attachTransVO = this.getActtachmentsTransVO1();
                            ViewObject CommentVO = this.getCommentsVO1();
                            ViewObject UserIdVO=this.getUserIdVO1();
                      
                            UserIdVO.setNamedWhereClauseParam("Uname",ADFContext.getCurrent().getSecurityContext().getUserName());
                            System.out.println(" hhhh "+UserIdVO.getQuery()+"  "+ADFContext.getCurrent().getSecurityContext().getUserName());
                            UserIdVO.executeQuery();
                            System.out.println("count is" +UserIdVO.getRowCount());
                            if(UserIdVO.getRowCount() >0) 
                            {    
                                if(UserIdVO.first().getAttribute("UserId")!=null)
                                {
                                    System.out.println("user id is" + UserIdVO.first().getAttribute("UserId").toString());    
                                    userId = UserIdVO.first().getAttribute("UserId").toString();
                                }
                            }
                                if(ticTVO.getRowCount() > 0) 
                                {
                                    System.out.println("inside if loop");
                                    Row ticRow = ticVO.createRow();
                                   // Row ticRow2 = CommentVO.getCurrentRow();
//                                    if(ticRow2 !=null){
//                                    System.out.println("  got comments row..");
//                                }
//                                else 
//                                {
//                                    System.out.println(" creating new row for commnets");
//                                    ticRow2 = CommentVO.createRow();
//                                    CommentVO.insertRow(ticRow2);
//                                }
                                ticRow.setAttribute("Ticketnumber",ticNumber);
                                ticRow.setAttribute("Projectname",ticTVO.first().getAttribute("Projectname"));
                                System.out.println("subject is "+ticTVO.first().getAttribute("Subject"));
                                ticRow.setAttribute("Subject",ticTVO.first().getAttribute("Subject"));
                                ticRow.setAttribute("Status","Open"); 
                                System.out.println("user id is "+userId);
                                System.out.println("assigned to is "+ticTVO.first().getAttribute("Assignedto"));
                                ticRow.setAttribute("Assignedto",assignedTo);
                                System.out.println(" Category "+ticTVO.first().getAttribute("Category"));
                                ticRow.setAttribute("Category",ticTVO.first().getAttribute("Category"));
                                ticRow.setAttribute("Priority",ticTVO.first().getAttribute("Priority"));
                                ticRow.setAttribute("Summary",ticTVO.first().getAttribute("Summary"));
                                ticRow.setAttribute("Description",ticTVO.first().getAttribute("Description"));
                                ticRow.setAttribute("Stepstoreproduce",ticTVO.first().getAttribute("Stepstoreproduce"));
                                ticRow.setAttribute("Additionalinformation",ticTVO.first().getAttribute("Additionalinformation"));
                                ticRow.setAttribute("CreationDate",ticTVO.first().getAttribute("Creationdate")); 
                                System.out.println("creation date is "+ticTVO.first().getAttribute("Creationdate"));
                                ticRow.setAttribute("ProjectId",ticTVO.first().getAttribute("ProjectId"));
                                ticRow.setAttribute("UserId",userId); 
                                System.out.println("closed date is "+closeddate);
                                ticRow.setAttribute("ClosedDate", closeddate);
                                Object ticId =ticRow.getAttribute("Ticketid");
                                ticVO.insertRow(ticRow);
//                                ticRow2.setAttribute("Ticketid", ticId);
//                                ticRow2.setAttribute("CreationId",userId); 
//                           
                                                       System.out.println("before Commit");
                                                       if(attachTransVO.getRowCount() >0){
                                                       Row[]  Trow = attachTransVO.getAllRowsInRange();
                                                       System.out.println(" attach row  count "+Trow.length);
                                                       for(int i =0 ;i< Trow.length;i++)
                                                       {
                                                       Row ticRow1 = AttachVO.createRow();
                                                           System.out.println(" Attachmentname .. "+Trow[i].getAttribute("Attachmentname"));
                                                           ticRow1.setAttribute("AttachmentName",Trow[i].getAttribute("Attachmentname"));
                                                           System.out.println(" AttachmentContentType .. "+Trow[i].getAttribute("Attachmentcontenttype"));
                                                           ticRow1.setAttribute("AttachmentContentType",Trow[i].getAttribute("Attachmentcontenttype"));
                                                           ticRow1.setAttribute("AttachmentData", Trow[i].getAttribute("Attachmetdata"));
                                                           ticRow1.setAttribute("Ticketid", ticId);
                                                           AttachVO.insertRow(ticRow1);
                                                           
                                                       }
                                                           
                                                       }    
                                                       
                                                       
                                                       
                                                       
                                                   }
                                                       
                                                       
                                             getDBTransaction().commit();          
                                                      
                                                   
                       
                                                   }
                      
                      catch(Exception e) {
                          System.out.println(" Error Message .. "+e.getMessage());
                          e.printStackTrace();
                          return "Error";
                      }
          return ticNumber;
    }
    
     /*
      * Creating attachment with generting number
      */
    public void CreateAttachment(){
                      }
    public String generateNumber() {
        //generate a 4 digit integer 1000 <10000
        int randomPIN = (int)(Math.random() * 9000) + 1000;
        //Store integer in a string
        String PINString = String.valueOf(randomPIN);
        System.out.println(" Number  ...  " + PINString);
        return PINString;
    }   
    public void updateAttachment(String ticId,String fileName,String contentType,oracle.jbo.domain.BlobDomain blob){

               ViewObject AttachVO = this.getAttachmentsVO1();
               ViewObject   arVO = this.getAttachmentsRVO1();
                   Row ticRow1 = AttachVO.createRow();
                   System.out.println("App module Attachmentname .. "+fileName);
                   ticRow1.setAttribute("AttachmentName", fileName);
                   ticRow1.setAttribute("AttachmentContentType", contentType);
                   ticRow1.setAttribute("AttachmentData", blob);
                   System.out.println("Appmodule Ticket id is"+ticId);
                   ticRow1.setAttribute("Ticketid", ticId);
                   AttachVO.insertRow(ticRow1);
                   getDBTransaction().commit();  
                   arVO.setWhereClause("TICKETID = "+ ticId);
                   arVO.executeQuery();
                               
                   
                   
           }
    
    public void forDelete(String AttachId,String ticId){
           
              System.out.println(" deleteing atatchment "+AttachId);
               ViewObject arVO = this.getAttachmentsRVO1();
               ViewObject AttachVO = this.getAttachmentsVO1();
               AttachVO.setWhereClause("AttachmentsEO.ATTACHMENT_ID = "+AttachId);
               AttachVO.executeQuery();
               if(AttachVO.getRowCount()>0){
               System.out.println(" got row for deletion ");
               AttachVO.first().remove();
               System.out.println(" end -->-- ");
               getDBTransaction().commit();
               }
               
               arVO.setWhereClause("TICKETID = "+ ticId);
               arVO.executeQuery();
           }
    public void onPopupComments(String ticId, String comments ){
                String userId=null;
                ViewObject UserIdVO=this.getUserIdVO1();        
                 UserIdVO.setNamedWhereClauseParam("Uname",ADFContext.getCurrent().getSecurityContext().getUserName());
                System.out.println(" hhhh "+UserIdVO.getQuery()+"  "+ADFContext.getCurrent().getSecurityContext().getUserName());
                UserIdVO.executeQuery();
                System.out.println("count is" +UserIdVO.getRowCount());
                if(UserIdVO.getRowCount() >0) 
                {    
                    if(UserIdVO.first().getAttribute("UserId")!=null)
                    {
                            System.out.println("user id is" + UserIdVO.first().getAttribute("UserId").toString());    
                            userId = UserIdVO.first().getAttribute("UserId").toString();
                    }
                }
                           ViewObject CommentVO = this.getCommentsVO1();
                           Row ticRow3 = CommentVO.createRow();
                           ticRow3.setAttribute("Ticketid", ticId);
                           ticRow3.setAttribute("CommentName", comments);
                           System.out.println(" urrrrrrrrrrrrrrrrrrrrrrr"+userId);
                           ticRow3.setAttribute("CreationId",userId);
                           CommentVO.insertRow(ticRow3);
           getDBTransaction().commit(); 
    }       
    public void onAdminProjectName(String projectName,Date startdate, Date enddate){
                ViewObject ProjectsVO = this.getProjectsVO1();
                Row ticRow4 = ProjectsVO.createRow();
                ticRow4.setAttribute("ProjectName", projectName);
                System.out.println("start date in am is "+ startdate);
                ticRow4.setAttribute("StartDate", startdate);
                ticRow4.setAttribute("EndDate", enddate);
                ProjectsVO.insertRow(ticRow4);
                getDBTransaction().commit(); 
            }
    public void addUser(){
                ViewObject Project_User_Mapping_TransVO=this.getProject_User_Mapping_TransVO1();
                ViewObject ProjectUserMappingVO=this.getProjectUserMappingVO1();
                Row RowAdd =ProjectUserMappingVO.createRow();
                RowAdd.setAttribute("UserId",Project_User_Mapping_TransVO.first().getAttribute("UserId"));
                RowAdd.setAttribute("ProjectId",Project_User_Mapping_TransVO.first().getAttribute("ProjectId"));  
                getDBTransaction().commit(); 
    }
    public void checkProjectName(){
        System.out.println(" check in project name ..");
        ViewObject ProjectUserVO=this.getProjectUserVO1();
        ViewObject ProjectsTranvo = this.getProject_User_Mapping_TransVO1();
        System.out.println(" project id.. "+ProjectsTranvo.first().getAttribute("ProjectId"));
        ProjectUserVO.setNamedWhereClauseParam("pid",ProjectsTranvo.first().getAttribute("ProjectId"));
        ProjectUserVO.executeQuery();
        System.out.println(" count.. "+ProjectUserVO.getRowCount());
    }
    
    public ViewObjectImpl getTicketingtoolRVO1() {
        return (ViewObjectImpl)findViewObject("TicketingtoolRVO1");
    }
    /**
     * Container's getter for StatusVO1.
     * @return StatusVO1
     */
    public ViewObjectImpl getStatusVO1() {
        return (ViewObjectImpl)findViewObject("StatusVO1");
    }
    /**
     * Container's getter for CategoryVO1.
     * @return CategoryVO1
     */
    public ViewObjectImpl getCategoryVO1() {
        return (ViewObjectImpl)findViewObject("CategoryVO1");
    }
    /**
     * Container's getter for PriorityVO1.
     * @return PriorityVO1
     */
    public ViewObjectImpl getPriorityVO1() {
        return (ViewObjectImpl)findViewObject("PriorityVO1");
    }
    /**
     * Container's getter for TicketingToolTransVO1.
     * @return TicketingToolTransVO1
     */
    public ViewObjectImpl getTicketingToolTransVO1() {
        return (ViewObjectImpl)findViewObject("TicketingToolTransVO1");
    }
    /**
     * Container's getter for AttachmentsVO1.
     * @return AttachmentsVO1
     */
    public ViewObjectImpl getAttachmentsVO1() {
        return (ViewObjectImpl)findViewObject("AttachmentsVO1");
    }

    /**
     * Container's getter for AttachmentsRVO1.
     * @return AttachmentsRVO1
     */
    public ViewObjectImpl getAttachmentsRVO1() {
        return (ViewObjectImpl)findViewObject("AttachmentsRVO1");
    }

    /**
     * Container's getter for TicketingtoolVO1.
     * @return TicketingtoolVO1
     */
    public TicketingtoolVOImpl getTicketingtoolVO1() {
        return (TicketingtoolVOImpl)findViewObject("TicketingtoolVO1");
    }


    /**
     * Container's getter for CommentsRVO1.
     * @return CommentsRVO1
     */
    public ViewObjectImpl getCommentsRVO1() {
        return (ViewObjectImpl)findViewObject("CommentsRVO1");
    }

    /**
     * Container's getter for CommentsVO1.
     * @return CommentsVO1
     */
    public ViewObjectImpl getCommentsVO1() {
        return (ViewObjectImpl)findViewObject("CommentsVO1");
    }


    /**
     * Container's getter for ActtachmentsTransVO1.
     * @return ActtachmentsTransVO1
     */
    public ViewObjectImpl getActtachmentsTransVO1() {
        return (ViewObjectImpl)findViewObject("ActtachmentsTransVO1");
    }

    /**
     * Container's getter for ProjectsVO1.
     * @return ProjectsVO1
     */
    public ViewObjectImpl getProjectsVO1() {
        return (ViewObjectImpl)findViewObject("ProjectsVO1");
    }

    /**
     * Container's getter for UsersVO1.
     * @return UsersVO1
     */
    public ViewObjectImpl getUsersVO1() {
        return (ViewObjectImpl)findViewObject("UsersVO1");
    }

    /**
     * Container's getter for ProjectsRVO1.
     * @return ProjectsRVO1
     */
    public ViewObjectImpl getProjectsRVO1() {
        return (ViewObjectImpl)findViewObject("ProjectsRVO1");
    }

    /**
     * Container's getter for UsersRVO1.
     * @return UsersRVO1
     */
    public ViewObjectImpl getUsersRVO1() {
        return (ViewObjectImpl)findViewObject("UsersRVO1");
    }

    /**
     * Container's getter for ProjectUserMappingVO1.
     * @return ProjectUserMappingVO1
     */
    public ViewObjectImpl getProjectUserMappingVO1() {
        return (ViewObjectImpl)findViewObject("ProjectUserMappingVO1");
    }

    /**
     * Container's getter for ProjectUserMappingViewRVO1.
     * @return ProjectUserMappingViewRVO1
     */
    public ViewObjectImpl getProjectUserMappingViewRVO1() {
        return (ViewObjectImpl)findViewObject("ProjectUserMappingViewRVO1");
    }

    /**
     * Container's getter for Project_User_Mapping_TransVO1.
     * @return Project_User_Mapping_TransVO1
     */
    public ViewObjectImpl getProject_User_Mapping_TransVO1() {
        return (ViewObjectImpl)findViewObject("Project_User_Mapping_TransVO1");
    }

    /**
     * Container's getter for ProjectUserVO1.
     * @return ProjectUserVO1
     */
    public ViewObjectImpl getProjectUserVO1() {
        return (ViewObjectImpl)findViewObject("ProjectUserVO1");
    }

    /**
     * Container's getter for UserIdVO1.
     * @return UserIdVO1
     */
    public ViewObjectImpl getUserIdVO1() {
        return (ViewObjectImpl)findViewObject("UserIdVO1");
    }

    /**
     * Container's getter for ProjectsTransVO1.
     * @return ProjectsTransVO1
     */
    public ViewObjectImpl getProjectsTransVO1() {
        return (ViewObjectImpl)findViewObject("ProjectsTransVO1");
    }
    
    public void onSearch(){
        
        ViewObject ticketRVO =  getTicketingtoolRVO1();
        ViewObject searchticketTransvo = getTicketingToolTransVO1();
        System.out.println("status is "+ searchticketTransvo.first().getAttribute("SearchStatus"));
        ticketRVO.setNamedWhereClauseParam("pstatus", searchticketTransvo.first().getAttribute("SearchStatus"));
      
        ticketRVO.setNamedWhereClauseParam("passignto", searchticketTransvo.first().getAttribute("Assignedto"));
       
        ticketRVO.setNamedWhereClauseParam("pcdate", searchticketTransvo.first().getAttribute("CreatedDate"));
       
        ticketRVO.setNamedWhereClauseParam("pticknum", searchticketTransvo.first().getAttribute("Ticketnumber"));
        
        ticketRVO.setNamedWhereClauseParam("pcldate", searchticketTransvo.first().getAttribute("ClosedDate"));
        ticketRVO.executeQuery();
        System.out.println("row count = " +ticketRVO.getRowCount());
        
    }
    


    
    public void searchSubmit(Date closeddate) {
    
    ViewObject ticVO = this.getTicketingtoolVO1();
    Row trow = ticVO.getCurrentRow();
    trow.setAttribute("ClosedDate", closeddate);
   oracle.jbo.domain.Date cdate=new oracle.jbo.domain.Date(new java.sql.Date(new java.util.Date().getTime()));  
    ticVO.insertRow(trow);
    trow.setAttribute("LastUpdatedDate", cdate);
    getDBTransaction().commit();
    
        }
    public void emptyAttachments(){
            ViewObjectImpl AttachVO = this.getActtachmentsTransVO1();
            ViewObjectImpl AttachRVO = this.getAttachmentsRVO1();
            AttachVO.executeEmptyRowSet();
            AttachRVO.executeEmptyRowSet();
        }
   
}
