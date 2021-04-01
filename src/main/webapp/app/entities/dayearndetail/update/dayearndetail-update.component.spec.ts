jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DayearndetailService } from '../service/dayearndetail.service';
import { IDayearndetail, Dayearndetail } from '../dayearndetail.model';

import { DayearndetailUpdateComponent } from './dayearndetail-update.component';

describe('Component Tests', () => {
  describe('Dayearndetail Management Update Component', () => {
    let comp: DayearndetailUpdateComponent;
    let fixture: ComponentFixture<DayearndetailUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dayearndetailService: DayearndetailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DayearndetailUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DayearndetailUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DayearndetailUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dayearndetailService = TestBed.inject(DayearndetailService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dayearndetail: IDayearndetail = { id: 456 };

        activatedRoute.data = of({ dayearndetail });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dayearndetail));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dayearndetail = { id: 123 };
        spyOn(dayearndetailService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dayearndetail });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dayearndetail }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dayearndetailService.update).toHaveBeenCalledWith(dayearndetail);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dayearndetail = new Dayearndetail();
        spyOn(dayearndetailService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dayearndetail });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dayearndetail }));
        saveSubject.complete();

        // THEN
        expect(dayearndetailService.create).toHaveBeenCalledWith(dayearndetail);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dayearndetail = { id: 123 };
        spyOn(dayearndetailService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dayearndetail });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dayearndetailService.update).toHaveBeenCalledWith(dayearndetail);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
