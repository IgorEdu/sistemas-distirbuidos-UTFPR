package com.UTFPR.commands;

import java.io.IOException;

public class CommandInvoker {
    public void executeCommand(Command command) throws IOException {
        command.execute();
    }
}