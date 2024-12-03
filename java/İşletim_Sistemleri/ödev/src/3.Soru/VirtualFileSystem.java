import java.util.Scanner;

public class VirtualFileSystem {

    Directory root; // K�k dizin
    int totalDiskSpace; // Toplam disk alan�
    int usedDiskSpace; // Kullan�lan disk alan�

    // Sanal dosya sisteminin yap�c� metodu
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
            System.out.println("Dizin Bulunamad�: " + path);
        }
    }

    // Yeni bir dosya ekleme metodu
    public void addFile(String path, String name) {
        Directory dir = navigateTo(path);
        if (dir != null) {
            dir.addFile(name);
        } else {
            System.out.println("Dizin Bulunamad�: " + path);
        }
    }

    // Dosyaya yaz� yazma metodu
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
                    System.out.println("Yeterli Haf�za Yok!");
                }
            } else {
                System.out.println("Dosya Bulunamad�: " + name);
            }
        } else {
            System.out.println("Dizin Bulunamad�: " + path);
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
                System.out.println("Dosya Bulunamad�: " + name);
            }
        } else {
            System.out.println("Dizin Bulunamad�: " + path);
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
                System.out.println("Dosya Bulunamad�: " + name);
            }
        } else {
            System.out.println("Dizin Bulunamad�: " + path);
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
                System.out.println("Dizin Bulunamad�: " + name);
            }
        } else {
            System.out.println("Parent Dizin Bulunamad�: " + path);
        }
    }

    // Dizin i�eri�ini listeleme metodu
    public void listDirectoryContents(String path) {
        Directory dir = navigateTo(path);
        if (dir != null) {
            dir.listContents();
        } else {
            System.out.println("Dizin Bulunamad�: " + path);
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

    // Kullan�lan disk alan�n� getirme metodu
    public int getUsedDiskSpace() {
        return usedDiskSpace;
    }

    // Ana metot (main) - Kullan�c� komutlar�n� okur ve i�ler
    public static void main(String[] args) {
        VirtualFileSystem vfs = new VirtualFileSystem(1000); // 1000 birimlik bir disk alan� olu�turulur
        Scanner scanner = new Scanner(System.in);
        String command;
        
        System.out.println("Sanal Dosya Sistemi Sim�lasyonu (VFS)");
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
                        System.out.println("Kullan�m: mkdir <path> <dirname>");
                    }
                    break;
                case "touch":
                    if (parts.length == 3) {
                        vfs.addFile(parts[1], parts[2]);
                    } else {
                        System.out.println("Kullan�m: touch <path> <filename>");
                    }
                    break;
                case "write":
                    if (parts.length >= 4) {
                        String path = parts[1];
                        String name = parts[2];
                        String content = command.substring(command.indexOf(parts[3]));
                        vfs.writeFile(path, name, content);
                    } else {
                        System.out.println("Kullan�m: write <path> <filename> <i�erik>");
                    }
                    break;
                case "read":
                    if (parts.length == 3) {
                        String content = vfs.readFile(parts[1], parts[2]);
                        System.out.println(content != null ? content : "Dosya Bulunamad�!");
                    } else {
                        System.out.println("Kullan�m: read <path> <filename>");
                    }
                    break;
                case "rm":
                    if (parts.length == 3) {
                        vfs.deleteFile(parts[1], parts[2]);
                    } else {
                        System.out.println("Kullan�m: rm <path> <filename>");
                    }
                    break;
                case "rmdir":
                    if (parts.length == 3) {
                        vfs.deleteDirectory(parts[1], parts[2]);
                    } else {
                        System.out.println("Kullan�m: rmdir <path> <dirname>");
                    }
                    break;
                case "ls":
                    if (parts.length == 2) {
                        vfs.listDirectoryContents(parts[1]);
                    } else {
                        System.out.println("Kullan�m: ls <path>");
                    }
                    break;
                default:
                    System.out.println("Bilinmeyen Komut: " + parts[0]);
            }

            System.out.println("Kullan�lan Disk Alan�: " + vfs.getUsedDiskSpace() + " / " + vfs.totalDiskSpace);
        }

        scanner.close();
    }
}