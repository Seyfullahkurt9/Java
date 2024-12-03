import java.util.Scanner;

public class VirtualFileSystem {

    Directory root; // Kök dizin
    int totalDiskSpace; // Toplam disk alaný
    int usedDiskSpace; // Kullanýlan disk alaný

    // Sanal dosya sisteminin yapýcý metodu
    public VirtualFileSystem(int totalDiskSpace) {
        this.root = new Directory("root");
        this.totalDiskSpace = totalDiskSpace;
        this.usedDiskSpace = 0;
    }

    // Yeni bir dizin ekleme metodu
    public void addDirectory(String path, String name) {
        Directory dir = navigateTo(path);
        if (dir != null) {
            dir.addDirectory(name);
        } else {
            System.out.println("Dizin Bulunamadý: " + path);
        }
    }

    // Yeni bir dosya ekleme metodu
    public void addFile(String path, String name) {
        Directory dir = navigateTo(path);
        if (dir != null) {
            dir.addFile(name);
        } else {
            System.out.println("Dizin Bulunamadý: " + path);
        }
    }

    // Dosyaya yazý yazma metodu
    public void writeFile(String path, String name, String content) {
        Directory dir = navigateTo(path);
        if (dir != null) {
            File file = dir.getFile(name);
            if (file != null) {
                int newSize = content.length();
                if (usedDiskSpace + newSize <= totalDiskSpace) {
                    file.write(content);
                    usedDiskSpace = usedDiskSpace + newSize;
                } else {
                    System.out.println("Yeterli Hafýza Yok!");
                }
            } else {
                System.out.println("Dosya Bulunamadý: " + name);
            }
        } else {
            System.out.println("Dizin Bulunamadý: " + path);
        }
    }

    // Dosya okuma metodu
    public String readFile(String path, String name) {
        Directory dir = navigateTo(path);
        if (dir != null) {
            File file = dir.getFile(name);
            if (file != null) {
                return file.read();
            } else {
                System.out.println("Dosya Bulunamadý: " + name);
            }
        } else {
            System.out.println("Dizin Bulunamadý: " + path);
        }
        return null;
    }

    // Dosya silme metodu
    public void deleteFile(String path, String name) {
        Directory dir = navigateTo(path);
        if (dir != null) {
            File file = dir.getFile(name);
            if (file != null) {
                usedDiskSpace -= file.getSize();
                dir.removeFile(name);
            } else {
                System.out.println("Dosya Bulunamadý: " + name);
            }
        } else {
            System.out.println("Dizin Bulunamadý: " + path);
        }
    }

    // Dizin silme metodu
    public void deleteDirectory(String path, String name) {
        Directory dir = navigateTo(path);
        if (dir != null) {
            Directory subDir = dir.getDirectory(name);
            if (subDir != null) {
                usedDiskSpace -= subDir.getSize();
                dir.removeDirectory(name);
            } else {
                System.out.println("Dizin Bulunamadý: " + name);
            }
        } else {
            System.out.println("Parent Dizin Bulunamadý: " + path);
        }
    }

    // Dizin içeriðini listeleme metodu
    public void listDirectoryContents(String path) {
        Directory dir = navigateTo(path);
        if (dir != null) {
            dir.listContents();
        } else {
            System.out.println("Dizin Bulunamadý: " + path);
        }
    }

    // Dizin yolunda gezinme metodu
    private Directory navigateTo(String path) {
        String[] parts = path.split("/");
        Directory current = root;
        for (String part : parts) {
            if (!part.isEmpty()) {
                if (part.equals("root") && current == root) {
                    continue;
                }
                Directory next = current.getDirectory(part);
                if (next == null) {
                    return null;
                }
                current = next;
            }
        }
        return current;
    }

    // Kullanýlan disk alanýný getirme metodu
    public int getUsedDiskSpace() {
        return usedDiskSpace;
    }

    // Ana metot (main) - Kullanýcý komutlarýný okur ve iþler
    public static void main(String[] args) {
        VirtualFileSystem vfs = new VirtualFileSystem(1000); // 1000 birimlik bir disk alaný oluþturulur
        Scanner scanner = new Scanner(System.in);
        String command;
        
        System.out.println("Sanal Dosya Sistemi Simülasyonu (VFS)");
        System.out.println("Komutlar: mkdir, touch, write, read, rm, rmdir, ls, exit");

        while (true) {
            System.out.print("> ");
            command = scanner.nextLine();
            String[] parts = command.split(" ");

            if (parts[0].equals("exit")) {
                break;
            }

            switch (parts[0]) {
                case "mkdir":
                    if (parts.length == 3) {
                        vfs.addDirectory(parts[1], parts[2]);
                    } else {
                        System.out.println("Kullaným: mkdir <path> <dirname>");
                    }
                    break;
                case "touch":
                    if (parts.length == 3) {
                        vfs.addFile(parts[1], parts[2]);
                    } else {
                        System.out.println("Kullaným: touch <path> <filename>");
                    }
                    break;
                case "write":
                    if (parts.length >= 4) {
                        String path = parts[1];
                        String name = parts[2];
                        String content = command.substring(command.indexOf(parts[3]));
                        vfs.writeFile(path, name, content);
                    } else {
                        System.out.println("Kullaným: write <path> <filename> <içerik>");
                    }
                    break;
                case "read":
                    if (parts.length == 3) {
                        String content = vfs.readFile(parts[1], parts[2]);
                        System.out.println(content != null ? content : "Dosya Bulunamadý!");
                    } else {
                        System.out.println("Kullaným: read <path> <filename>");
                    }
                    break;
                case "rm":
                    if (parts.length == 3) {
                        vfs.deleteFile(parts[1], parts[2]);
                    } else {
                        System.out.println("Kullaným: rm <path> <filename>");
                    }
                    break;
                case "rmdir":
                    if (parts.length == 3) {
                        vfs.deleteDirectory(parts[1], parts[2]);
                    } else {
                        System.out.println("Kullaným: rmdir <path> <dirname>");
                    }
                    break;
                case "ls":
                    if (parts.length == 2) {
                        vfs.listDirectoryContents(parts[1]);
                    } else {
                        System.out.println("Kullaným: ls <path>");
                    }
                    break;
                default:
                    System.out.println("Bilinmeyen Komut: " + parts[0]);
            }

            System.out.println("Kullanýlan Disk Alaný: " + vfs.getUsedDiskSpace() + " / " + vfs.totalDiskSpace);
        }

        scanner.close();
    }
}