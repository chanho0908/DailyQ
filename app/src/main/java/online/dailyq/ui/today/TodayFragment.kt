package online.dailyq.ui.today

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import online.dailyq.R
import online.dailyq.api.response.Question
import online.dailyq.databinding.FragmentTodayBinding
import online.dailyq.ui.base.BaseFragment
import online.dailyq.ui.write.WriteActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TodayFragment : BaseFragment() {

    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!

    private var quest: Question? = null

    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        initLauncher()
        initView()
        return binding.root
    }

    private fun initView(){
        with(binding){
            writeButton.setOnClickListener {
                startForResult.launch(Intent(requireContext(), WriteActivity::class.java).apply {
                    putExtra(WriteActivity.EXTRA_QID, quest?.id)
                    putExtra(WriteActivity.EXTRA_MODE, WriteActivity.Mode.WRITE)
                })
            }
            editButton.setOnClickListener {
                startForResult.launch(Intent(requireContext(), WriteActivity::class.java).apply {
                    putExtra(WriteActivity.EXTRA_QID, quest?.id)
                    putExtra(WriteActivity.EXTRA_MODE, WriteActivity.Mode.EDIT)
                })
            }
            deleteButton.setOnClickListener {
                showDeleteConfirmDialog()
            }

            viewLifecycleOwner.lifecycleScope.launch {
                val questionResponse = api.getQuestion(LocalDate.now())
                if (questionResponse.isSuccessful) {
                    quest = questionResponse.body()!!

                    val dateFormatter = DateTimeFormatter.ofPattern("yyyy. M. d.")

                    date.text = dateFormatter.format(quest?.id)
                    question.text = quest?.text

                    setupAnswer()
                }
            }
        }
    }

    private fun initLauncher(){
        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                lifecycleScope.launch {
                    setupAnswer()
                }
            }
        }
    }

    private fun showDeleteConfirmDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.dialog_msg_are_you_sure_to_delete)
            .setPositiveButton(R.string.ok) { dialog, which ->
                lifecycleScope.launch {
                    val deleteResponse = api.deleteAnswer(quest!!.id)
                    if (deleteResponse.isSuccessful) {
                        binding.answerArea.isVisible = false
                        binding.writeButton.isVisible = true
                    }
                }
            }.setNegativeButton(R.string.cancel) { dialog, which ->

            }.show()
    }

    private suspend fun setupAnswer() {
        val question = quest ?: return

        val answer = api.getAnswer(question.id).body()
        binding.answerArea.isVisible = answer != null
        binding.textAnswer.text = answer?.text

        binding.writeButton.isVisible = answer == null
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
