jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CheckinBakService } from '../service/checkin-bak.service';
import { ICheckinBak, CheckinBak } from '../checkin-bak.model';

import { CheckinBakUpdateComponent } from './checkin-bak-update.component';

describe('Component Tests', () => {
  describe('CheckinBak Management Update Component', () => {
    let comp: CheckinBakUpdateComponent;
    let fixture: ComponentFixture<CheckinBakUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let checkinBakService: CheckinBakService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CheckinBakUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CheckinBakUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckinBakUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      checkinBakService = TestBed.inject(CheckinBakService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const checkinBak: ICheckinBak = { id: 456 };

        activatedRoute.data = of({ checkinBak });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(checkinBak));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkinBak = { id: 123 };
        spyOn(checkinBakService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkinBak });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: checkinBak }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(checkinBakService.update).toHaveBeenCalledWith(checkinBak);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkinBak = new CheckinBak();
        spyOn(checkinBakService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkinBak });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: checkinBak }));
        saveSubject.complete();

        // THEN
        expect(checkinBakService.create).toHaveBeenCalledWith(checkinBak);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkinBak = { id: 123 };
        spyOn(checkinBakService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkinBak });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(checkinBakService.update).toHaveBeenCalledWith(checkinBak);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
