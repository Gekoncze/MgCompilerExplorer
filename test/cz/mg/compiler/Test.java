package cz.mg.compiler;

import cz.mg.compiler.utilities.debug.Text;
import cz.mg.compiler.entities.input.ExternalFileInput;
import cz.mg.compiler.entities.logical.project.FilePath;
import cz.mg.compiler.tasks.compiler.CompileProjectTask;
import cz.mg.compilerexplorer.gui.MainWindow;


public class Test {
    public static void main(String[] args) {
        ExternalFileInput.workingDirectory = "/home/me/Plocha/Dev/Java/MgCompilerExplorer/test/cz/mg/compiler/project/";

        System.out.print("Compiling... ");

        Compiler compiler = new Compiler();
        compiler.getTasks().getMainTasks().addLast(new CompileProjectTask(
                compiler.getEntities(),
                new FilePath(new Text("TestProject.mg", ""))
        ));
        compiler.run();

        System.out.println("DONE");

        MainWindow mainWindow = new MainWindow(compiler);
        mainWindow.setVisible(true);
    }
}
