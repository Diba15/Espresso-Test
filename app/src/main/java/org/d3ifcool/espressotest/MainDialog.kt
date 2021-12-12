package org.d3ifcool.espressotest

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import org.d3ifcool.espressotest.data.Mahasiswa
import org.d3ifcool.espressotest.databinding.DialogMainBinding

class MainDialog : DialogFragment() {
    private lateinit var binding: DialogMainBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        binding = DialogMainBinding.inflate(inflater, null, false)

        val builder = AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.tambah_mahasiswa)
            setView(binding.root)
            setPositiveButton(R.string.simpan) { _, _ ->
                val mahasiswa = getData() ?: return@setPositiveButton
                val listener = requireActivity() as DialogListener
                listener.processDialog(mahasiswa)
            }
            setNegativeButton(R.string.batal) { _, _ -> dismiss() }
        }
        return builder.create()
    }

    private fun showMessage(messageResId: Int) {
        Toast.makeText(requireContext(), messageResId, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }

    private fun getData(): Mahasiswa? {
        if (binding.nimEditText.text.isEmpty()) {
            showMessage(R.string.nim_wajib_diisi)
            return null
        }

        if (binding.nimEditText.text.length != 10) {
            showMessage(R.string.nim_harus_10chars)
            return null
        }


        if (binding.namaEditText.text.isEmpty()) {
            showMessage(R.string.nama_wajib_diisi)
            return null
        }

        return Mahasiswa(
            nim = binding.nimEditText.text.toString(),
            nama = binding.namaEditText.text.toString()
        )
    }

    interface DialogListener {
        fun processDialog(mahasiswa: Mahasiswa)
    }
}