package teammates.ui.controller;

import java.util.ArrayList;
import java.util.List;

import teammates.common.datatransfer.AccountAttributes;
import teammates.common.datatransfer.CommentAttributes;
import teammates.common.datatransfer.InstructorAttributes;
import teammates.common.datatransfer.StudentAttributes;
import teammates.common.datatransfer.StudentProfileAttributes;
import teammates.common.util.Const;
import teammates.common.util.Sanitizer;
import teammates.ui.template.Comment;
import teammates.ui.template.CommentsForStudentsTable;
import teammates.ui.template.StudentProfile;

public class InstructorStudentRecordsPageData extends PageData {

    private String courseId;
    private String studentName;
    private String studentEmail;
    private String googleId;
    private String showCommentBox;
    private StudentProfile studentProfile;
    private List<CommentsForStudentsTable> commentsForStudentTable;
    private List<String> sessionNames;

    public InstructorStudentRecordsPageData(AccountAttributes account, StudentAttributes student,
                                            String courseId, String showCommentBox, StudentProfileAttributes spa,
                                            List<CommentAttributes> comments, List<String> sessionNames,
                                            InstructorAttributes instructor) {
        super(account, student);
        this.courseId = courseId;
        this.studentName = Sanitizer.sanitizeForHtml(student.name);
        this.studentEmail = student.email;
        this.googleId = account.googleId;
        this.showCommentBox = showCommentBox;
        if (spa != null) {
            String pictureUrl = Const.ActionURIs.STUDENT_PROFILE_PICTURE
                              + "?" + Const.ParamsNames.BLOB_KEY + "=" + spa.pictureKey
                              + "&" + Const.ParamsNames.USER_ID + "=" + account.googleId;
            if (spa.pictureKey.isEmpty()) {
                pictureUrl = Const.SystemParams.DEFAULT_PROFILE_PICTURE_PATH;
            }
            this.studentProfile = new StudentProfile(this.studentName, spa, pictureUrl);
        }
        List<Comment> commentDivs = new ArrayList<Comment>();
        for (CommentAttributes comment : comments) {
            Comment commentDiv = new Comment(comment, "You", student.name + " (" + student.team + ", " + student.email + ")");
            String whoCanSeeComment = getTypeOfPeopleCanViewComment(comment);
            commentDiv.setVisibilityIcon(whoCanSeeComment);
            commentDiv.setEditDeleteEnabled(false);
            commentDiv.setNotFromCommentsPage(student.email);
            commentDiv.setNumComments(comments.size());
            commentDivs.add(commentDiv);
        }
        this.commentsForStudentTable = new ArrayList<CommentsForStudentsTable>();
        CommentsForStudentsTable commentsForStudent = new CommentsForStudentsTable("You", commentDivs);
        commentsForStudent.setInstructorAllowedToGiveComment(instructor.isAllowedForPrivilege(student.section,
                                        Const.ParamsNames.INSTRUCTOR_PERMISSION_GIVE_COMMENT_IN_SECTIONS));
        commentsForStudentTable.add(commentsForStudent);
        this.sessionNames = sessionNames;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getShowCommentBox() {
        return showCommentBox;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public List<CommentsForStudentsTable> getCommentsForStudentTable() {
        return commentsForStudentTable;
    }

    public List<String> getSessionNames() {
        return sessionNames;
    }

}
