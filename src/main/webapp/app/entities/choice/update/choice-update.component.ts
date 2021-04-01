import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IChoice, Choice } from '../choice.model';
import { ChoiceService } from '../service/choice.service';
import { IQuestion } from 'app/entities/question/question.model';
import { QuestionService } from 'app/entities/question/service/question.service';

@Component({
  selector: 'jhi-choice-update',
  templateUrl: './choice-update.component.html',
})
export class ChoiceUpdateComponent implements OnInit {
  isSaving = false;

  questionsSharedCollection: IQuestion[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    createdat: [null, [Validators.required]],
    updatedat: [null, [Validators.required]],
    text: [null, [Validators.required, Validators.maxLength(191)]],
    votes: [null, [Validators.required]],
    question: [null, Validators.required],
  });

  constructor(
    protected choiceService: ChoiceService,
    protected questionService: QuestionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ choice }) => {
      if (choice.id === undefined) {
        const today = dayjs().startOf('day');
        choice.createdat = today;
        choice.updatedat = today;
      }

      this.updateForm(choice);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const choice = this.createFromForm();
    if (choice.id !== undefined) {
      this.subscribeToSaveResponse(this.choiceService.update(choice));
    } else {
      this.subscribeToSaveResponse(this.choiceService.create(choice));
    }
  }

  trackQuestionById(index: number, item: IQuestion): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChoice>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(choice: IChoice): void {
    this.editForm.patchValue({
      id: choice.id,
      createdat: choice.createdat ? choice.createdat.format(DATE_TIME_FORMAT) : null,
      updatedat: choice.updatedat ? choice.updatedat.format(DATE_TIME_FORMAT) : null,
      text: choice.text,
      votes: choice.votes,
      question: choice.question,
    });

    this.questionsSharedCollection = this.questionService.addQuestionToCollectionIfMissing(this.questionsSharedCollection, choice.question);
  }

  protected loadRelationshipsOptions(): void {
    this.questionService
      .query()
      .pipe(map((res: HttpResponse<IQuestion[]>) => res.body ?? []))
      .pipe(
        map((questions: IQuestion[]) =>
          this.questionService.addQuestionToCollectionIfMissing(questions, this.editForm.get('question')!.value)
        )
      )
      .subscribe((questions: IQuestion[]) => (this.questionsSharedCollection = questions));
  }

  protected createFromForm(): IChoice {
    return {
      ...new Choice(),
      id: this.editForm.get(['id'])!.value,
      createdat: this.editForm.get(['createdat'])!.value ? dayjs(this.editForm.get(['createdat'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedat: this.editForm.get(['updatedat'])!.value ? dayjs(this.editForm.get(['updatedat'])!.value, DATE_TIME_FORMAT) : undefined,
      text: this.editForm.get(['text'])!.value,
      votes: this.editForm.get(['votes'])!.value,
      question: this.editForm.get(['question'])!.value,
    };
  }
}
