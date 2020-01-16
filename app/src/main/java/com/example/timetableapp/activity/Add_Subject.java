package com.example.timetableapp.activity;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.timetableapp.R;
import com.example.timetableapp.model.Subject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Add_Subject extends AppCompatActivity implements android.view.View.OnClickListener{

    private ImageView add_subject, cancel, refresh;
    private EditText subject, venue, professor, courseName;
    private CheckBox labCourse;
    private RadioButton HSS, engg;
    private HashMap<String, Subject> subjectHashMap = new HashMap<>();
    private RecyclerView recyclerView;
    public Integer position = -1;
    StringAdapter adapter;
    String year_branch = "1CSE";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    HashMap<Integer, String> btech_des = new HashMap<>();
    HashMap<Integer, String> branches = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_add_subject);

//        subjectHashMap = (HashMap<String, Subject>) getIntent().getSerializableExtra("Subject");


        add_subject = findViewById(R.id.add_subject_button);
        cancel = findViewById(R.id.cancel_button);
        refresh = findViewById(R.id.refresh_button);
        subject = findViewById(R.id.add_subject_edit_text);
        venue = findViewById(R.id.venue_edit_text);
        professor = findViewById(R.id.professor_edit_text);
        courseName = findViewById(R.id.course_name_edit_text);
        labCourse = findViewById(R.id.lab_course);
        recyclerView = findViewById(R.id.subjects_listView);
        HSS = findViewById(R.id.hss);
        engg = findViewById(R.id.engg);
        btech_des.put(1, "BTech");
        btech_des.put(2, "BDes");
        branches.put(1, "CSE");
        branches.put(2, "ECE");
        branches.put(3, "ME");
        branches.put(4, "CE");
        branches.put(6, "BT");
        branches.put(7, "CL");
        branches.put(8, "EEE");
        branches.put(21, "EP");
        branches.put(22, "CST");
        branches.put(23, "MC");
        add_subject.setOnClickListener(this);
        cancel.setOnClickListener(this);
        refresh.setOnClickListener(this);
        loadSubjects();


    }
    public void loadSubjects()
    {

        db
                .collection("timetable")
                .document("BTech")
                .collection(year_branch)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TAG", "listen:error", e);
                            return;
                        }
//                        subjectHashMap.clear();
                        for (DocumentChange document : snapshots.getDocumentChanges()) {
                            switch (document.getType())
                            {
                                case  REMOVED:
                                    db
                                            .collection("timetable")
                                            .document("BTech")
                                            .collection(year_branch)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task)
                                                {
                                                    subjectHashMap.clear();
                                                    if(task.isSuccessful())
                                                    {
                                                        for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                                                        {
                                                            Subject temp = document.toObject(Subject.class);
                                                            subjectHashMap.put(temp.getCourseCode(),temp);
                                                        }

                                                        ArrayList<String> subjectList = new ArrayList<>();
                                                        for (Map.Entry mapElement : subjectHashMap.entrySet()) {
                                                            String sub = ((String)mapElement.getKey());
                                                            subjectList.add(sub);
                                                        }
                                                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                                        StringAdapter adapter = new StringAdapter(getApplicationContext(),subjectList, subjectHashMap);
                                                        recyclerView.setAdapter(adapter);
                                                    }
                                                    else
                                                    {
                                                        //TODO: exception handling

                                                    }
                                                }
                                            });
                                    break;
                                    default:
                                        Subject temp = document.getDocument().toObject(Subject.class);
                                        subjectHashMap.put(temp.getCourseCode(),temp);
                                        ArrayList<String> subjectList = new ArrayList<>();
                                        for (Map.Entry mapElement : subjectHashMap.entrySet()) {
                                            String sub = ((String)mapElement.getKey());
                                            subjectList.add(sub);
                                        }
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                        adapter = new StringAdapter(getApplicationContext(),subjectList, subjectHashMap);
                                        recyclerView.setAdapter(adapter);
                            }
                        }


                    }
                });

    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.add_subject_button:
                AddSubjectToDatabase();
                RefreshUI();
                break;
            case R.id.cancel_button:
                DelSubjectFromDatabase();
                RefreshUI();
                break;
            case R.id.refresh_button:
                position = -1;
                adapter.notifyDataSetChanged();
                RefreshUI();
            default:
                break;
        }
    }

    private void RefreshUI() {
        subject.setText("");
        venue.setText("");
        professor.setText("");
        courseName.setText("");
        labCourse.setChecked(false);
        HSS.setSelected(false);
        engg.setSelected(true);
    }

    private void AddSubjectToDatabase()
    {
        String subject_code = subject.getText().toString(), subject_venue = venue.getText().toString();
        String subject_professor = professor.getText().toString();
        //TODO : set yearBrancchh
        String subject_name = courseName.getText().toString().trim();
        Boolean lab = labCourse.isSelected();
        Boolean hss = HSS.isChecked();
        //TODO: add branch
        Subject subject = new Subject(subject_code,subject_professor,subject_venue,subject_name,lab,hss);
        db.collection("timetable").document("BTech").collection(year_branch).document(subject_code).set(subject);
    }

    private void DelSubjectFromDatabase()
    {
        String subject_code = subject.getText().toString();
        db.collection("timetable").document("BTech").collection(year_branch).document(subject_code).delete();
    }

    private void updateUi(Subject curSubject)
    {
        subject.setText(curSubject.getCourseCode());
        venue.setText(curSubject.getVenue());
        professor.setText(curSubject.getProfessor());
        courseName.setText(curSubject.getCourseName());
        labCourse.setChecked(curSubject.getLabCourse());
        HSS.setSelected(curSubject.getHSS());
        engg.setSelected(!curSubject.getHSS());

    }

    public class StringAdapter extends RecyclerView.Adapter<StringAdapter.ViewHolder>
    {
        private ArrayList<String> subjects;
        private HashMap<String, Subject> subjectHashMap;
        private Context context;
        List<View> itemViewList  = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        public StringAdapter(Context context, ArrayList<String> subjects, HashMap<String, Subject> subjectHashMap)
        {
            this.context = context;
            this.subjects = subjects;
            this.subjectHashMap = subjectHashMap;
        }
        @NonNull
        @Override
        public StringAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(context).inflate(R.layout.string_list_item, parent, false);
            final StringAdapter.ViewHolder myiewHolder = new StringAdapter.ViewHolder(itemView);
            itemViewList.add(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(View temp : itemViewList)
                    {
                        if (itemViewList.get(myiewHolder.getAdapterPosition()) == temp)
                        {
                            temp.setBackgroundColor(Color.parseColor("#d9d9d9"));
                        }else temp.setBackgroundColor(Color.parseColor("#fafafa"));


                    }
                    position = myiewHolder.getAdapterPosition();
                    if(position >=0 && position < subjects.size())
                    {
                        Subject curSubject = subjectHashMap.get(subjects.get(position));
                        updateUi(curSubject);
                    }
                    notifyDataSetChanged();
                    position = -1;
                }
            });
            return myiewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull StringAdapter.ViewHolder holder, int position) {
            String slot = subjects.get(position);
            holder.bindTo(slot);
        }

        @Override
        public int getItemCount() {
            return subjects.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            private TextView subject;
            private LinearLayout ll;
            ViewHolder(@NonNull View itemView)
            {
                super(itemView);
                subject = itemView.findViewById(R.id.subject_item_text_view);
                ll = itemView.findViewById(R.id.subject_item_linear_layout);
            }

            void bindTo(String slot)
            {
                subject.setText(slot);
            }

        }
    }




}

