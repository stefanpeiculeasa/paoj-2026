package com.pao.laboratory05.audit;

import java.time.LocalDateTime;
import java.util.Arrays;

public class AngajatService {
    private static class Holder {
        private static final AngajatService INSTANCE = new AngajatService();
    }
    
    private Angajat[] angajati;
    private AuditEntry[] auditLog;
    
    private AngajatService() {
        angajati = new Angajat[0];
        auditLog = new AuditEntry[0];
    }
    
    public static AngajatService getInstance() {
        return Holder.INSTANCE;
    }

    private void logAction(String action, String target) {
        AuditEntry entry = new AuditEntry(action, target, LocalDateTime.now().toString());
        AuditEntry[] copy = new AuditEntry[auditLog.length + 1];
        System.arraycopy(auditLog, 0, copy, 0, auditLog.length);
        copy[auditLog.length] = entry;
        auditLog = copy;
    }
    
    public void addAngajat(Angajat a) {
        Angajat[] copy = new Angajat[angajati.length + 1];
        System.arraycopy(angajati, 0, copy, 0, angajati.length);
        copy[angajati.length] = a;
        angajati = copy;
        logAction("ADD", a.getNume());
        System.out.println("Angajat adaugat: " + a.getNume());
    }
    
    public void printAll() {
        for (Angajat a : angajati) {
            System.out.println(a);
        }
    }
    
    public void listBySalary() {
        Angajat[] copy = angajati.clone();
        Arrays.sort(copy);
        int i = 1;
        System.out.println("angajati dupa salariu (descrescator)");
        for (Angajat a : copy) {
            System.out.println(i++ + ". " + a);
        }
    }
    
    public void findByDepartament(String numeDept) {
        logAction("FIND_BY_DEPT", numeDept);
        System.out.println("ngajati din " + numeDept);
        boolean gasit = false;
        for (Angajat a : angajati) {
            if (a.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                System.out.println(a);
                gasit = true;
            }
        }
        if (!gasit) {
            System.out.println("Niciun angajat in departamentul: " + numeDept);
        }
    }

    public void printAuditLog() {
        System.out.println("Audit Log");
        for (AuditEntry entry : auditLog) {
            System.out.println(entry);
        }
    }
}