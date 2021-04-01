jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CheckCzlService } from '../service/check-czl.service';
import { ICheckCzl, CheckCzl } from '../check-czl.model';

import { CheckCzlUpdateComponent } from './check-czl-update.component';

describe('Component Tests', () => {
  describe('CheckCzl Management Update Component', () => {
    let comp: CheckCzlUpdateComponent;
    let fixture: ComponentFixture<CheckCzlUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let checkCzlService: CheckCzlService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CheckCzlUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CheckCzlUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckCzlUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      checkCzlService = TestBed.inject(CheckCzlService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const checkCzl: ICheckCzl = { id: 456 };

        activatedRoute.data = of({ checkCzl });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(checkCzl));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkCzl = { id: 123 };
        spyOn(checkCzlService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkCzl });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: checkCzl }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(checkCzlService.update).toHaveBeenCalledWith(checkCzl);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkCzl = new CheckCzl();
        spyOn(checkCzlService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkCzl });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: checkCzl }));
        saveSubject.complete();

        // THEN
        expect(checkCzlService.create).toHaveBeenCalledWith(checkCzl);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const checkCzl = { id: 123 };
        spyOn(checkCzlService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkCzl });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(checkCzlService.update).toHaveBeenCalledWith(checkCzl);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
