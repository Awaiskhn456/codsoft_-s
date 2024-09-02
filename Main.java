
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private int enrolledStudents;
    private String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolledStudents = 0;
        this.schedule = schedule;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents;
    }

    public String getSchedule() {
        return schedule;
    }

    public boolean enroll() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    public boolean drop() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Code: %s\nTitle: %s\nDescription: %s\nCapacity: %d\nAvailable Slots: %d\nSchedule: %s\n",
                code, title, description, capacity, getAvailableSlots(), schedule);
    }
}


class Student {
    private String id;
    private String name;
    private List<Course> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean registerCourse(Course course) {
        if (course.enroll()) {
            registeredCourses.add(course);
            return true;
        }
        return false;
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.remove(course) && course.drop()) {
            return true;
        }
        return false;
    }
}


class CourseManager {
    private Map<String, Course> courses;

    public CourseManager() {
        courses = new HashMap<>();
    }

    public void addCourse(Course course) {
        courses.put(course.getCode(), course);
    }

    public Course getCourse(String code) {
        return courses.get(code);
    }

    public void listCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        for (Course course : courses.values()) {
            System.out.println(course);
        }
    }
}



class StudentManager {
    private Map<String, Student> students;

    public StudentManager() {
        students = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }

    public Student getStudent(String id) {
        return students.get(id);
    }
}


public class Main {
    private static CourseManager courseManager = new CourseManager();
    private static StudentManager studentManager = new StudentManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
     
        courseManager.addCourse(new Course("CS101", "Introduction to Computer Science", "Learn the basics of computer science.", 30, "Mon/Wed 10-11 AM"));
        courseManager.addCourse(new Course("MATH101", "Calculus I", "Introduction to calculus.", 25, "Tue/Thu 2-3:30 PM"));

        
        studentManager.addStudent(new Student("S001", "Alice Smith"));
        studentManager.addStudent(new Student("S002", "Bob Johnson"));

        while (true) {
            System.out.println("\n1. List Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. Drop Course");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    courseManager.listCourses();
                    break;
                case 2:
                    registerForCourse();
                    break;
                case 3:
                    dropCourse();
                    break;
                case 4:
                    System.out.println("Exiting.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void registerForCourse() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentManager.getStudent(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        Course course = courseManager.getCourse(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.registerCourse(course)) {
            System.out.println("Successfully registered for " + course.getTitle());
        } else {
            System.out.println("Registration failed. Course may be full.");
        }
    }

    private static void dropCourse() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentManager.getStudent(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course code to drop: ");
        String courseCode = scanner.nextLine();
        Course course = courseManager.getCourse(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.dropCourse(course)) {
            System.out.println("Successfully dropped course " + course.getTitle());
        } else {
            System.out.println("Drop failed. You may not be registered for this course.");
        }
    }
}
