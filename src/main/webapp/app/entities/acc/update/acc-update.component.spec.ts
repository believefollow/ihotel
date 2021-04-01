jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AccService } from '../service/acc.service';
import { IAcc, Acc } from '../acc.model';

import { AccUpdateComponent } from './acc-update.component';

describe('Component Tests', () => {
  describe('Acc Management Update Component', () => {
    let comp: AccUpdateComponent;
    let fixture: ComponentFixture<AccUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let accService: AccService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AccUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AccUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      accService = TestBed.inject(AccService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const acc: IAcc = { id: 456 };

        activatedRoute.data = of({ acc });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(acc));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const acc = { id: 123 };
        spyOn(accService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ acc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: acc }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(accService.update).toHaveBeenCalledWith(acc);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const acc = new Acc();
        spyOn(accService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ acc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: acc }));
        saveSubject.complete();

        // THEN
        expect(accService.create).toHaveBeenCalledWith(acc);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const acc = { id: 123 };
        spyOn(accService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ acc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(accService.update).toHaveBeenCalledWith(acc);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
