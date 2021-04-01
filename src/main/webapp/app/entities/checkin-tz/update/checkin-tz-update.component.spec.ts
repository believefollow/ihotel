jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CheckinTzService } from '../service/checkin-tz.service';
import { ICheckinTz, CheckinTz } from '../checkin-tz.model';

import { CheckinTzUpdateComponent } from './checkin-tz-update.component';

describe('Component Tests', () => {
  describe('CheckinTz Management Update Component', () => {
    let comp: CheckinTzUpdateComponent;
    let fixture: ComponentFixture<CheckinTzUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let checkinTzService: CheckinTzService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CheckinTzUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CheckinTzUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckinTzUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      checkinTzService = TestBed.inject(CheckinTzService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const checkinTz: ICheckinTz = { id: 456 };

        activatedRoute.data = of({ checkinTz });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(checkinTz));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkinTz = { id: 123 };
        spyOn(checkinTzService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkinTz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: checkinTz }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(checkinTzService.update).toHaveBeenCalledWith(checkinTz);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkinTz = new CheckinTz();
        spyOn(checkinTzService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkinTz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: checkinTz }));
        saveSubject.complete();

        // THEN
        expect(checkinTzService.create).toHaveBeenCalledWith(checkinTz);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkinTz = { id: 123 };
        spyOn(checkinTzService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkinTz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(checkinTzService.update).toHaveBeenCalledWith(checkinTz);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
