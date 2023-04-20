package com.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class DbHelper {

    private SessionFactory sessionFactory;
    private Session session;

    private static DbHelper instance;

    private DbHelper() {
        // Create Configuration
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();

        session.close();
    }

    public static synchronized DbHelper getInstance() {
        if (instance == null) {
            instance = new DbHelper();
        }
        return instance;
    }

    public void addTask(Task task) {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(task);
        transaction.commit();

// Cerrar sesión de Hibernate
        session.close();
    }

    public void editTask(Task task) throws Exception {
        session = sessionFactory.openSession();

        // Obtener objeto Task por su id
        Task old = session.get(Task.class, task.getId());
        if (old != null) {
            // Realizar cambios en las propiedades del objeto Task
            old.setTitulo(task.getTitulo());
            old.setDescripcion(task.getDescripcion());
            old.setFecha(task.getFecha());

            // Iniciar transacción y actualizar objeto Task en la base de datos
            Transaction transaction = session.beginTransaction();
            session.merge(old   );
            transaction.commit();

        } else {
           throw new Exception("No existe la tarea a actualizar en BD");
        }

// Cerrar sesión de Hibernate
        session.close();

    }

    public void deleteTask(Task task) {
        session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();
        session.remove(task);
        transaction.commit();

        session.close();
    }

    public List<Task> getTaskList() {

        session = sessionFactory.openSession();
// Ejecutar consulta HQL para obtener lista de objetos Task
        Query<Task> query = session.createQuery("FROM Task", Task.class);
        List<Task> taskList = query.list();

        session.close();
        return taskList;
    }

}
