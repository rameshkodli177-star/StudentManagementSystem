import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentManager {
    private ArrayList<Student> students = new ArrayList<>();
    private final String FILE_NAME = "students.dat";
    private Scanner sc = new Scanner(System.in);

    public StudentManager() {
        loadFromFile();
    }

    public void addStudent() {
        System.out.println("\n--- Add New Student ---");
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (findStudentById(id) != null) {
            System.out.println("Error: Student with ID " + id + " already exists!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Course: ");
        String course = sc.nextLine();
        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();
        sc.nextLine();

        students.add(new Student(id, name, age, course, marks));
        saveToFile();
        System.out.println("Student added successfully!");
    }

    public void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("--------------------------------------------------------------------------");
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Total Students: " + students.size());
    }

    public void updateStudent() {
        System.out.println("\n--- Update Student ---");
        System.out.print("Enter Student ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("Current Details → " + student);
        System.out.println("Enter new details (press Enter to keep old value):\n");

        System.out.print("New Name [" + student.getName() + "]: ");
        String name = sc.nextLine();
        if (!name.trim().isEmpty()) student.setName(name);

        System.out.print("New Age [" + student.getAge() + "]: ");
        String ageStr = sc.nextLine();
        if (!ageStr.trim().isEmpty()) student.setAge(Integer.parseInt(ageStr));

        System.out.print("New Course [" + student.getCourse() + "]: ");
        String course = sc.nextLine();
        if (!course.trim().isEmpty()) student.setCourse(course);

        System.out.print("New Marks [" + student.getMarks() + "]: ");
        String marksStr = sc.nextLine();
        if (!marksStr.trim().isEmpty()) student.setMarks(Double.parseDouble(marksStr));

        saveToFile();
        System.out.println("Student updated successfully!");
    }

    public void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        System.out.print("Enter Student ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        students.remove(student);
        saveToFile();
        System.out.println("Student deleted successfully!");
    }

    public void searchStudent() {
        System.out.println("\n--- Search Student ---");
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Student student = findStudentById(id);
        if (student != null) {
            System.out.println("Student Found → " + student);
        } else {
            System.out.println("Student not found!");
        }
    }

    private Student findStudentById(int id) {
        for (Student s : students) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (ArrayList<Student>) ois.readObject();
            System.out.println("Loaded " + students.size() + " student(s) from file.");
        } catch (Exception e) {
            System.out.println("Error loading data.");
            students = new ArrayList<>();
        }
    }
}
