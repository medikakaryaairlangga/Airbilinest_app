package com.mka.airbilinest


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class PatientAdapter(
    private val patients: List<Patient>,
    private val onPatientClick: (Patient) -> Unit,
    private val onDeleteClick: (Patient) -> Unit // Add delete handler
) : RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false)
        return PatientViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patients[position]
        holder.bind(patient)
        holder.itemView.setOnClickListener { onPatientClick(patient) }

        // Handle the delete button click
        holder.deleteButton.setOnClickListener { onDeleteClick(patient) }
    }

    override fun getItemCount(): Int {
        return patients.size
    }

    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.patientName)
        private val idTextView: TextView = itemView.findViewById(R.id.patientId)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(patient: Patient) {
            nameTextView.text = patient.name
            idTextView.text = patient.id
        }
    }
}
