jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AccPService } from '../service/acc-p.service';
import { IAccP, AccP } from '../acc-p.model';

import { AccPUpdateComponent } from './acc-p-update.component';

describe('Component Tests', () => {
  describe('AccP Management Update Component', () => {
    let comp: AccPUpdateComponent;
    let fixture: ComponentFixture<AccPUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let accPService: AccPService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AccPUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AccPUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccPUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      accPService = TestBed.inject(AccPService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const accP: IAccP = { id: 456 };

        activatedRoute.data = of({ accP });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(accP));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accP = { id: 123 };
        spyOn(accPService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accP });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: accP }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(accPService.update).toHaveBeenCalledWith(accP);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accP = new AccP();
        spyOn(accPService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accP });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: accP }));
        saveSubject.complete();

        // THEN
        expect(accPService.create).toHaveBeenCalledWith(accP);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accP = { id: 123 };
        spyOn(accPService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accP });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(accPService.update).toHaveBeenCalledWith(accP);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
