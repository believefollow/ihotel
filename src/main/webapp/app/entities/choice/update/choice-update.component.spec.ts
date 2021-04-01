jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ChoiceService } from '../service/choice.service';
import { IChoice, Choice } from '../choice.model';
import { IQuestion } from 'app/entities/question/question.model';
import { QuestionService } from 'app/entities/question/service/question.service';

import { ChoiceUpdateComponent } from './choice-update.component';

describe('Component Tests', () => {
  describe('Choice Management Update Component', () => {
    let comp: ChoiceUpdateComponent;
    let fixture: ComponentFixture<ChoiceUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let choiceService: ChoiceService;
    let questionService: QuestionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ChoiceUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ChoiceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChoiceUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      choiceService = TestBed.inject(ChoiceService);
      questionService = TestBed.inject(QuestionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Question query and add missing value', () => {
        const choice: IChoice = { id: 456 };
        const question: IQuestion = { id: 96952 };
        choice.question = question;

        const questionCollection: IQuestion[] = [{ id: 74252 }];
        spyOn(questionService, 'query').and.returnValue(of(new HttpResponse({ body: questionCollection })));
        const additionalQuestions = [question];
        const expectedCollection: IQuestion[] = [...additionalQuestions, ...questionCollection];
        spyOn(questionService, 'addQuestionToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ choice });
        comp.ngOnInit();

        expect(questionService.query).toHaveBeenCalled();
        expect(questionService.addQuestionToCollectionIfMissing).toHaveBeenCalledWith(questionCollection, ...additionalQuestions);
        expect(comp.questionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const choice: IChoice = { id: 456 };
        const question: IQuestion = { id: 50586 };
        choice.question = question;

        activatedRoute.data = of({ choice });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(choice));
        expect(comp.questionsSharedCollection).toContain(question);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const choice = { id: 123 };
        spyOn(choiceService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ choice });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: choice }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(choiceService.update).toHaveBeenCalledWith(choice);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const choice = new Choice();
        spyOn(choiceService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ choice });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: choice }));
        saveSubject.complete();

        // THEN
        expect(choiceService.create).toHaveBeenCalledWith(choice);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const choice = { id: 123 };
        spyOn(choiceService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ choice });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(choiceService.update).toHaveBeenCalledWith(choice);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackQuestionById', () => {
        it('Should return tracked Question primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackQuestionById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
