package com.UTFPR.server.commands;

import com.UTFPR.shared.commands.Command;

import java.io.IOException;

public class CommandInvoker {
    public void executeCommand(Command command) throws IOException {
        command.execute();
    }
}