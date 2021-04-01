jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CheckinService } from '../service/checkin.service';
import { ICheckin, Checkin } from '../checkin.model';

import { CheckinUpdateComponent } from './checkin-update.component';

describe('Component Tests', () => {
  describe('Checkin Management Update Component', () => {
    let comp: CheckinUpdateComponent;
    let fixture: ComponentFixture<CheckinUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let checkinService: CheckinService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CheckinUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CheckinUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckinUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      checkinService = TestBed.inject(CheckinService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const checkin: ICheckin = { id: 456 };

        activatedRoute.data = of({ checkin });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(checkin));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkin = { id: 123 };
        spyOn(checkinService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkin });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: checkin }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(checkinService.update).toHaveBeenCalledWith(checkin);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkin = new Checkin();
        spyOn(checkinService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkin });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: checkin }));
        saveSubject.complete();

        // THEN
        expect(checkinService.create).toHaveBeenCalledWith(checkin);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkin = { id: 123 };
        spyOn(checkinService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkin });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(checkinService.update).toHaveBeenCalledWith(checkin);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
