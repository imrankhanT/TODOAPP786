package com.bridgelabz.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.Labels;
import com.bridgelabz.model.Notes;
import com.bridgelabz.model.User;

@Repository
public class NotesDAOImpl implements NotesDAO {

	@Autowired
	SessionFactory sessionFactory;

	public String addNotes(Notes notes) {
		Session session = sessionFactory.getCurrentSession();

		try {
			session.save(notes);
			return "saved";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void updateNotes(Notes notes) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(notes);
	}

	public int deleteNotes(int id) {
		Session session = sessionFactory.getCurrentSession();

		try {
			Query<?> query = session.createQuery("delete Notes where id =:id");
			query.setParameter("id", id);
			int count = query.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public Notes getNotesById(int id) {
		Session session = sessionFactory.getCurrentSession();

		try {
			Query<?> query = session.createQuery("from Notes where id =:id");
			query.setParameter("id", id);
			Notes notes = (Notes) query.uniqueResult();
			return notes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Notes> getAllNotes(int User_Id) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println("inside getallNotes");
		try {
			Query<?> query = session.createQuery("from Notes where User_Id =:User_Id");
			query.setParameter("User_Id", User_Id);
			List<Notes> allNotes = (List<Notes>) query.list();

			Criteria criteria = session.createCriteria(Notes.class);
			criteria.createAlias("userId", "user");
			criteria.add(Restrictions.eq("user.id", User_Id));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			List<Notes> list1 = criteria.list();
			allNotes.addAll(list1);

			for (Notes notes : allNotes) {
				notes.getLables().size();
			}

			System.out.println(allNotes);
			return allNotes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteCollabUser(String email, Notes notes) {
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("from User where email =:email");
		query.setParameter("email", email);
		List list = (List) query.uniqueResult();
		list.remove(notes.getUserId());

		query.executeUpdate();
		return false;
	}

	public boolean insertLabel(Labels labels) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.save(labels);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int deletLabels(int id) {
		Session session = sessionFactory.getCurrentSession();

		try {
			Query<?> query = session.createQuery("delete from Labels where id =:id");
			query.setParameter("id", id);
			int count = query.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<Labels> getAllLabels(User user) {
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("from Labels where user =:user");
		query.setParameter("user", user);
		List<Labels> lables = (List<Labels>) query.list();

		try {
			return lables;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Labels getLabelById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("from Labels where id =:id");
		query.setParameter("id", id);
		Labels labels = (Labels) query.uniqueResult();
		if (labels != null)
			return (Labels) labels;
		else
			return null;
	}

	public void updateLable(Labels labels) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(labels);
	}

}
