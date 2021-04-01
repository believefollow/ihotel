jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CardysqService } from '../service/cardysq.service';
import { ICardysq, Cardysq } from '../cardysq.model';

import { CardysqUpdateComponent } from './cardysq-update.component';

describe('Component Tests', () => {
  describe('Cardysq Management Update Component', () => {
    let comp: CardysqUpdateComponent;
    let fixture: ComponentFixture<CardysqUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let cardysqService: CardysqService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CardysqUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CardysqUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CardysqUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      cardysqService = TestBed.inject(CardysqService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const cardysq: ICardysq = { id: 456 };

        activatedRoute.data = of({ cardysq });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(cardysq));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cardysq = { id: 123 };
        spyOn(cardysqService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cardysq });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cardysq }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(cardysqService.update).toHaveBeenCalledWith(cardysq);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cardysq = new Cardysq();
        spyOn(cardysqService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cardysq });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cardysq }));
        saveSubject.complete();

        // THEN
        expect(cardysqService.create).toHaveBeenCalledWith(cardysq);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cardysq = { id: 123 };
        spyOn(cardysqService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cardysq });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(cardysqService.update).toHaveBeenCalledWith(cardysq);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
