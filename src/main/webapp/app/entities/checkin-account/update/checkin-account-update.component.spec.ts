jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CheckinAccountService } from '../service/checkin-account.service';
import { ICheckinAccount, CheckinAccount } from '../checkin-account.model';

import { CheckinAccountUpdateComponent } from './checkin-account-update.component';

describe('Component Tests', () => {
  describe('CheckinAccount Management Update Component', () => {
    let comp: CheckinAccountUpdateComponent;
    let fixture: ComponentFixture<CheckinAccountUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let checkinAccountService: CheckinAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CheckinAccountUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CheckinAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckinAccountUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      checkinAccountService = TestBed.inject(CheckinAccountService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const checkinAccount: ICheckinAccount = { id: 456 };

        activatedRoute.data = of({ checkinAccount });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(checkinAccount));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkinAccount = { id: 123 };
        spyOn(checkinAccountService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkinAccount });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: checkinAccount }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(checkinAccountService.update).toHaveBeenCalledWith(checkinAccount);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkinAccount = new CheckinAccount();
        spyOn(checkinAccountService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkinAccount });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: checkinAccount }));
        saveSubject.complete();

        // THEN
        expect(checkinAccountService.create).toHaveBeenCalledWith(checkinAccount);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkinAccount = { id: 123 };
        spyOn(checkinAccountService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkinAccount });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(checkinAccountService.update).toHaveBeenCalledWith(checkinAccount);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
