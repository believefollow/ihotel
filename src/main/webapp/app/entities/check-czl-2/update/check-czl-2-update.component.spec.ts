jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CheckCzl2Service } from '../service/check-czl-2.service';
import { ICheckCzl2, CheckCzl2 } from '../check-czl-2.model';

import { CheckCzl2UpdateComponent } from './check-czl-2-update.component';

describe('Component Tests', () => {
  describe('CheckCzl2 Management Update Component', () => {
    let comp: CheckCzl2UpdateComponent;
    let fixture: ComponentFixture<CheckCzl2UpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let checkCzl2Service: CheckCzl2Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CheckCzl2UpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CheckCzl2UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckCzl2UpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      checkCzl2Service = TestBed.inject(CheckCzl2Service);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const checkCzl2: ICheckCzl2 = { id: 456 };

        activatedRoute.data = of({ checkCzl2 });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(checkCzl2));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkCzl2 = { id: 123 };
        spyOn(checkCzl2Service, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkCzl2 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: checkCzl2 }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(checkCzl2Service.update).toHaveBeenCalledWith(checkCzl2);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkCzl2 = new CheckCzl2();
        spyOn(checkCzl2Service, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkCzl2 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: checkCzl2 }));
        saveSubject.complete();

        // THEN
        expect(checkCzl2Service.create).toHaveBeenCalledWith(checkCzl2);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkCzl2 = { id: 123 };
        spyOn(checkCzl2Service, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkCzl2 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(checkCzl2Service.update).toHaveBeenCalledWith(checkCzl2);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
