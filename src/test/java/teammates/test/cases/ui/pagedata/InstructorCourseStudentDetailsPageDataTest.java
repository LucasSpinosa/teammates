package teammates.test.cases.ui.pagedata;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import teammates.common.datatransfer.AccountAttributes;
import teammates.common.datatransfer.StudentAttributes;
import teammates.common.datatransfer.StudentProfileAttributes;
import teammates.common.util.Const;
import teammates.test.cases.BaseTestCase;
import teammates.ui.controller.InstructorCourseStudentDetailsPageData;
import teammates.ui.template.StudentInfoTable;
import teammates.ui.template.StudentProfile;

public class InstructorCourseStudentDetailsPageDataTest extends BaseTestCase {
    private StudentAttributes inputStudent;
    private StudentProfileAttributes inputStudentProfile;
    private String pictureUrl;
    private boolean isAbleToAddComment;
    private boolean hasSection;
    private String commentRecipient;
    
    @BeforeClass
    public static void classSetUp() {
        printTestClassHeader();
    }
    
    @Test
    public void allTests() {
        boolean hasPictureKey = true;
        InstructorCourseStudentDetailsPageData data = createData(hasPictureKey);
        testData(data);
        
        hasPictureKey = false;
        data = createData(hasPictureKey);
        testData(data);
    }
    
    private void testData(InstructorCourseStudentDetailsPageData data) {
        testStudentProfile(data.getStudentProfile());
        testStudentInfoTable(data.getStudentInfoTable());
        assertEquals(commentRecipient, data.getCommentRecipient());
    }

    private void testStudentProfile(StudentProfile studentProfile) {
        assertNotNull(studentProfile);
        
        assertNotNull(studentProfile.getPictureUrl());
        assertEquals(pictureUrl, studentProfile.getPictureUrl());
        assertEquals(inputStudent.name, studentProfile.getName());
        assertEquals(inputStudentProfile.shortName, studentProfile.getShortName());
        assertEquals(inputStudentProfile.gender, studentProfile.getGender());
        assertEquals(inputStudentProfile.email, studentProfile.getEmail());
        assertEquals(inputStudentProfile.institute, studentProfile.getInstitute());
        assertEquals(inputStudentProfile.nationality, studentProfile.getNationality());
        assertEquals(inputStudentProfile.moreInfo, studentProfile.getMoreInfo());
    }

    private void testStudentInfoTable(StudentInfoTable studentInfoTable) {
        assertNotNull(studentInfoTable);
        
        assertEquals(inputStudent.name, studentInfoTable.getName());
        assertEquals(inputStudent.email, studentInfoTable.getEmail());
        assertEquals(inputStudent.section, studentInfoTable.getSection());
        assertEquals(inputStudent.team, studentInfoTable.getTeam());
        assertEquals(inputStudent.comments, studentInfoTable.getComments());
        assertEquals(inputStudent.course, studentInfoTable.getCourse());
        assertEquals(isAbleToAddComment, studentInfoTable.isAbleToAddComment());
        assertEquals(hasSection, studentInfoTable.getHasSection());
    }

    private InstructorCourseStudentDetailsPageData createData(boolean hasPictureKey) {
        String name = "John Doe";
        String email = "john@doe.com";
        
        String comments = "This is a comment for John Doe.";
        String courseId = "CourseForJohnDoe";
        String team = "TeamForJohnDoe";
        String section = "SectionForJohnDoe";
        
        String shortName = "John";
        String institute = "InstituteForJohnDoe";
        String nationality = "NationForJohnDoe";
        String gender = Const.GenderTypes.MALE;
        String moreInfo = "Information for John Doe.";
        String pictureKey = hasPictureKey ? "ThisIsAPictureKeyForJohnDoe" : null;
        
        if (hasPictureKey) {
            this.pictureUrl =  Const.ActionURIs.STUDENT_PROFILE_PICTURE + "?"
                               + Const.ParamsNames.BLOB_KEY + "=" + pictureKey + "&"
                               + Const.ParamsNames.USER_ID + "=null";
        } else {
            this.pictureUrl = Const.SystemParams.DEFAULT_PROFILE_PICTURE_PATH;
        }
        
        inputStudent = new StudentAttributes(null, email, name, comments, courseId, team, section);
        inputStudentProfile = new StudentProfileAttributes(
                null, shortName, email, institute, nationality, gender, moreInfo, pictureKey);
        isAbleToAddComment = true;
        hasSection = true;
        commentRecipient = "cmtRec";
        
        return new InstructorCourseStudentDetailsPageData(
                new AccountAttributes(), inputStudent, inputStudentProfile, isAbleToAddComment, hasSection, commentRecipient);
    }
    
    
}